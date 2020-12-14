package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.SQLFormatUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.system.entity.SystemDept;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.system.mapper.SystemDeptMapper;
import com.duo.modules.system.mapper.SystemUserMapper;
import com.duo.modules.tool.entity.ToolData;
import com.duo.modules.tool.entity.ToolExcelImport;
import com.duo.modules.tool.entity.ToolExcelImportColumn;
import com.duo.modules.tool.entity.ToolFunction;
import com.duo.modules.tool.mapper.*;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/26]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class POIService extends BaseService {
    @Autowired
    ToolExcelImportMapper toolExcelImportMapper;
    @Autowired
    ToolExcelImportColumnMapper toolExcelImportColumnMapper;
    @Autowired
    SystemDeptMapper systemDeptMapper;
    @Autowired
    ToolDataMapper toolDataMapper;
    @Autowired
    SystemUserMapper systemUserMapper;

    /**
     * excel导入
     * @param funInfo
     * @param importSet
     * @return
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public boolean importMain( MultipartFile file,ToolFunction funInfo ,ToolExcelImport  importSet)  {
        String tableName= "";
        String dbSource= DataSourceNames.DEFAULT;
        tableName=funInfo.getTable_name().trim();
        tableName= Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        if(StringUtils.isNotEmpty(funInfo.getDb_source())){
            dbSource=funInfo.getDb_source().trim();
        }
        DynamicDataSource.setDataSource(dbSource);//设置指定数据源
        log.info("set datasource is " + dbSource);
        try {
            List<ToolExcelImportColumn> columnList= getExcelImportColumns(importSet.getImport_id());
            List<Map<String,Object>> datas= importExcel(file,columnList,importSet.getHead_index());
            String keycolumn = getTableKeyIdColumn(tableName);//主键列
            SqlSession session = getSession(dbSource);
            SqlMapper sqlMapper = new SqlMapper(session);

            for(Map<String,Object> mp:datas){
                //获取newid
                String newid = getKeyUID();//树型后续支持
                mp.put(keycolumn, newid);
                //根据功能配置，对页面无赋值字段设置默认值
                mp=addDefaultValue2Map(mp);

                //通过页面参数创建对象
                Class clazz = MemCache._entitys.get(tableName);
                BaseEntity entity = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
                entity = addDataInfo(entity);
                //打印
                E2MapUtil.pringEntity(entity, clazz);
                //插入记录
                getMapper(tableName).insertSelective(entity);

                //执行detsql1
                if(StringUtils.isNotEmpty(importSet.getPost_detsql1())){
                    sqlMapper.update(SQLFormatUtil.formatSQL(getDBType(dbSource),importSet.getPost_detsql1()),mp);
                }
                //执行detsql2
                if(StringUtils.isNotEmpty(importSet.getPost_detsql2())){
                    sqlMapper.update(SQLFormatUtil.formatSQL(getDBType(dbSource),importSet.getPost_detsql2()),mp);
                }
            }

            //执行sql1
            if(StringUtils.isNotEmpty(importSet.getPost_sql1())){
                sqlMapper.update(SQLFormatUtil.formatSQL(getDBType(dbSource),importSet.getPost_sql1()));
            }
            //执行sql2
            if(StringUtils.isNotEmpty(importSet.getPost_sql2())){
                sqlMapper.update(SQLFormatUtil.formatSQL(getDBType(dbSource),importSet.getPost_sql2()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入Excel文件出错",e);
            return  false;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }

        return true;
    }


    /**
     * 获取excel导入设置信息
     * @param funId
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    @DataSource(name = DataSourceNames.PLATFORM)
    public ToolExcelImport getExcelImportInfo(String funId){

        ToolExcelImport query=new ToolExcelImport();
        query.setFun_id(funId);
        query.setAuditing("1");
        ToolExcelImport  entity= toolExcelImportMapper.selectOne(query);

        return entity;
    }

    /**
     * 获取excel导入字段配置信息
     * @param importId
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolExcelImportColumn> getExcelImportColumns(String importId){

        ToolExcelImportColumn query=new ToolExcelImportColumn();
        query.setImport_id(importId);
        List<ToolExcelImportColumn> list = toolExcelImportColumnMapper.select(query);

        return list;
    }


    /**
     * 部门name转id
     * @param name
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    @DataSource(name = DataSourceNames.DEFAULT)
    public String changeDept(String name){

        SystemDept query=new SystemDept();
        query.setDept_name(name);
         SystemDept user  = systemDeptMapper.selectOne(query);

        return user==null?name:user.getDept_id();
    }


    /**
     * 下拉name转value
     * @param name
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    @DataSource(name = DataSourceNames.PLATFORM)
    public String changeData(String name){

        ToolData  query=new ToolData();
        query.setData_text(name);
        ToolData data  = toolDataMapper.selectOne(query);

        return data==null?name:data.getData_value();
    }

    /**
     * 用户name转id
     * @param name
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    @DataSource(name = DataSourceNames.DEFAULT)
    public String changeUser(String name){

        SystemUser  query=new SystemUser();
        query.setUser_name(name);
        SystemUser user  = systemUserMapper.selectOne(query);

        return user==null?name:user.getUser_id();
    }



    /**
     * 导入数据（单页）
     *
     * @param file        文件
     * @param sheetIndex  页名的索引(从0开始，-1代表全部页)
     * @param headerIndex 表头的索引（用于获取共多少列以及第几行开始读数据）
     * @return
     * @throws  Exception
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public   List<Map<String,Object>> importExcel(MultipartFile file, List<ToolExcelImportColumn> columnList, int sheetIndex, int headerIndex) throws Exception {
        Workbook workbook = null;
        //返回的data
        List<Map<String,Object>> data = new ArrayList<>();
        workbook = getWorkbook(file);
        //导入某一页
        if (sheetIndex != -1 && sheetIndex > -1) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            List<Map<String,Object>> lists = importOneSheet(sheetIndex,sheet,columnList, headerIndex);
            data.addAll(lists);
        } else {
            //导入全部
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                if (sheet == null) {
                    continue;
                }
                List<Map<String,Object>> lists = importOneSheet(i,sheet,columnList, headerIndex);
                data.addAll(lists);
            }
        }
        return data;
    }

    /**
     * 导入数据（所有页）
     *
     * @param file        文件
     * @param headerIndex 表头的索引（用于获取共多少列以及第几行开始读数据）
     * @return
     * @throws  Exception
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public   List<Map<String,Object>> importExcel(MultipartFile file, List<ToolExcelImportColumn> columnList, int headerIndex) throws Exception {
        return importExcel(file,columnList, -1, headerIndex);
    }


    /**
     * 获取一个sheet里的数据
     *
     * @param sheet
     * @param headerIndex
     * @return
     * @throws Exception
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    private   List<Map<String,Object>> importOneSheet(int sheetno,Sheet sheet,List<ToolExcelImportColumn> columnList, int headerIndex) throws Exception {
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        int row = sheet.getLastRowNum();
        //row = -1 表格中没有数据
        //row = headerIndex 表格中表头以下没有数据（指没有有用数据）
        if (row == -1 || row == headerIndex) {
            throw new Exception("表格中没有有用数据!");
        }
        //通过表头获取共多少列
        int coloumNum = columnList.size();//sheet.getRow(headerIndex).getPhysicalNumberOfCells();
        //从表头下一行开始取数据
        for (int i = headerIndex + 1; i <= row; i++) {
            Row row1 = sheet.getRow(i);
            Map mp=new HashMap<String,Object>();
            if (row1 != null) {
                for (int j = 0; j < coloumNum; j++) {
                    ToolExcelImportColumn column=columnList.get(j);
                    String value= column.getValue_type().equals("default")?
                            String.valueOf(BaseService.getDefaultValue(column.getDefault_value(),"String")): getCellValue(row1.getCell(Integer.parseInt(column.getCol_index())));
                    //必填项检查
                    if(column.getIs_null().equals("1")&& StringUtils.isEmpty(value)){
                        throw new Exception("表格中[sheet"+(sheetno+1)+"]的第"+(i+1)+"行，第"+(Integer.parseInt(column.getCol_index())+1)+"列为必填项，发现却为空!");
                    }

                    if(column.getChange_type().equals("dept")){
                        value=changeDept(value);
                    }else if(column.getChange_type().equals("user")){
                        value=changeUser(value);
                    }else if(column.getChange_type().equals("data")){
                        value=changeData(value);
                    }
                    mp.put(column.getColumn_name(),value);
                }
            }
            data.add(mp);
        }
        return data;
    }

    /**
     * 获取workbook
     *
     * @return
     */
    private   Workbook getWorkbook(MultipartFile file) throws Exception {
        Workbook workbook = null;
        //获取文件名
        String fileName = file.getOriginalFilename();
        //判断文件格式
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else {
            throw new Exception("文件格式有误!");
        }
        return workbook;
    }


    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case NUMERIC:
                cellValue = df.format(cell.getNumericCellValue()).toString();
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue.trim();
    }

}
