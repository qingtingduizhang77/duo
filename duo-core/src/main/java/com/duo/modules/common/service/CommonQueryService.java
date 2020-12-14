package com.duo.modules.common.service;

import com.ace.cache.annotation.Cache;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.SQLPageHelper;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import com.duo.modules.tool.entity.ToolFunction;
import com.duo.modules.tool.entity.ToolFunctionColumn;
import com.duo.modules.tool.entity.ToolFunctionEventSql;
import com.duo.modules.tool.entity.ToolFunctionQuery;
import com.duo.modules.tool.mapper.ToolFunctionEventSqlMapper;
import com.github.abel533.sql.SqlMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Cacheable;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommonQueryService extends BaseService{

    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SystemAttachmentMapper systemAttachmentMapper;


    /**
     *  通过SQL查询
     * @param requestMap
     * @return
     */
   // public  static  SQLFormatProperties sQLFormatProperties = (SQLFormatProperties)SpringContextHolder.getBean("SQLFormatProperties");
    //@Cache(key="resultSet:",expire=1)
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public PageResultSet<Map<String,Object>> findBySQLPageMp(Map<String, Object> requestMap) {
        PageResultSet<Map<String,Object>> resultSet = new PageResultSet<>();
        String tableName= (String) requestMap.get("tableName");
        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        String whereSQL="";
        if(requestMap.containsKey("table_id")){
            whereSQL=" where table_id=#{table_id}";
            if(requestMap.get("table_id").equals("")) {
                resultSet.setRows(null);
                resultSet.setTotal(0);
                return resultSet;
            }
        }
        String orderBySQL="order by order_index asc";
        if(requestMap.containsKey("sort")){
            if(!requestMap.get("sort").equals("")) {
                orderBySQL ="order by "+ requestMap.get("sort")+" "+requestMap.get("order");
            }
        }
        List<Map<String,Object>> rowList=sqlMapper.selectList(SQLPageHelper.SqlPage(getDBType(DataSourceNames.DEFAULT),"select * from "+tableName+whereSQL,orderBySQL),requestMap);
        Map<String,Object> cntMp=sqlMapper.selectOne(SQLPageHelper.CountSqlPage(getDBType(DataSourceNames.DEFAULT),"select * from "+tableName+whereSQL),requestMap);
        session.close();
      //  log.info("rowList size():"+rowList.size());
       // log.info("row  count:"+cntMp.get("cnt"));
        resultSet.setRows(rowList);
        resultSet.setTotal(Integer.parseInt(String.valueOf(cntMp.get("cnt")==null?cntMp.get("CNT"):cntMp.get("cnt"))));
        return resultSet;
    }


    /**
     *  通过SQL查询
     * @param funId
     * @param requestMap
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public PageResultSet<Map<String,Object>> findBySQLPage(String funId, Map<String, Object> requestMap) throws Exception {
        PageResultSet<Map<String,Object>> resultSet = new PageResultSet<>();

        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        if(funInfo==null){
            log.error("获取功能“"+funId+"”信息失败！请联系管理员！");
            return resultSet;
        }
        String tableName=funInfo.getTable_name();
        String selectSQL=MemCache._selectSQLMap.get(funId);
        if(selectSQL==null){
            StringBuffer selectSQLsb=new StringBuffer("select ");
            List<ToolFunctionColumn> columns=layoutService.getFunctionColumn(funId);
            for(ToolFunctionColumn column:columns){
               if(!"sql".equals(column.getGroup_name()) ) continue;
                selectSQLsb.append(column.getColumn_statement()).append(",");
                MemCache._columnsMap.put(funId+"."+column.getColumn_name(),column.getColumn_statement());
            }
            selectSQL=selectSQLsb.substring(0,selectSQLsb.length()-1)+" ";
            if ( MemCache.getSystemParameter("selectSQLCache").equals("true")) {//是否查询字段缓存
                MemCache._selectSQLMap.put(funId,selectSQL);//存到缓存
            }
        }
        String fromSQL=funInfo.getFrom_sql();
        String whereSQL=funInfo.getWhere_sql();
        if(StringUtils.isNotEmpty(whereSQL)){
                whereSQL = " where " + whereSQL;
        }else{
            whereSQL="";
        }
        String orderBySQL=funInfo.getOrderby_sql();
        if(StringUtils.isNotEmpty(orderBySQL)){
            orderBySQL=" order by "+orderBySQL;
        }else{
            orderBySQL="";
        }

        //传入baseSQL
        if(requestMap.containsKey("baseSQL")) {
            String baseSQL= (String) requestMap.get("baseSQL") ;
            if(!StringUtils.emptyToDefault(baseSQL,"").equals("")) {
                baseSQL+=" ";
                if(baseSQL.split("'").length % 2!=1||baseSQL.indexOf("(")>baseSQL.indexOf(")")||baseSQL.split("\\(").length!=baseSQL.split("\\)").length) {//可疑SQL侵入
                    log.error("查询sql出错！可疑SQL侵入！"+baseSQL);
                    throw new Exception("可疑SQL侵入！");
                }

                if (whereSQL.length() > 0) {
                    whereSQL += " and (" + baseSQL + ") ";
                } else {
                    whereSQL = " where (" + baseSQL + ") ";
                }
            }
        }
        //传入orgSQL
        if(requestMap.containsKey("orgSQL")) {
            String orgSQL= (String) requestMap.get("orgSQL") ;
            if(!StringUtils.emptyToDefault(orgSQL,"").equals("")) {
                orgSQL+=" ";
                if(orgSQL.split("'").length % 2!=1||orgSQL.indexOf("(")>orgSQL.indexOf(")")||orgSQL.split("\\(").length!=orgSQL.split("\\)").length) {//可疑SQL侵入
                    log.error(orgSQL.split("'").length +"查询sql出错！可疑SQL侵入！"+orgSQL);
                    throw new Exception("可疑SQL侵入！");
                }

                if (whereSQL.length() > 0) {
                    whereSQL += " and (" + orgSQL + ") ";
                } else {
                    whereSQL = " where (" + orgSQL + ") ";
                }
            }
        }
        //点击树节点查询
        if(requestMap.containsKey("pId")) {
            String pId= (String) requestMap.get("pId") ;
            // if(StringUtils.isNotEmpty(pId)) {
            if (StringUtils.isNotEmpty(funInfo.getTree_forwordwhere())) {
                if (whereSQL.length() > 0) {
                    whereSQL += " and (" + funInfo.getTree_forwordwhere() + ") ";
                } else {
                    whereSQL =  " where (" + funInfo.getTree_forwordwhere() + ") ";
                }
            }
            //  }
        }
        //外键
        if(requestMap.containsKey("foreignColumn")&&requestMap.containsKey("foreignKeyId")) {
            String foreignKeyId = (String) requestMap.get("foreignKeyId") ;
            if(StringUtils.isNotEmpty(foreignKeyId)) {
                String foreignColumn = (String)  requestMap.get("foreignColumn") ;
                //如果fun定义了外键，按fun的。没有则用页面传来的
            if (StringUtils.isNotEmpty(funInfo.getForeign_column())) {
                foreignColumn=funInfo.getForeign_column();
            }
            if (StringUtils.isNotEmpty(foreignColumn)) {
                if (whereSQL.length() > 0) {
                    whereSQL += " and (" + foreignColumn + "=#{foreignKeyId}" + ") ";
                } else {
                    whereSQL = " where (" + foreignColumn + "=#{foreignKeyId}" + ") ";
                }
            }
            }
        }
        //查询条
        if(requestMap.containsKey("query__selectCombo")){
            String queryId=(String) requestMap.get("query__selectCombo") ;
            if(StringUtils.isNotEmpty(queryId)) {
                ToolFunctionQuery query = layoutService.getFunctionQueryById(queryId);
                if (StringUtils.isNotEmpty(query.getWhere_sql())) {
                    if (whereSQL.length() > 0) {
                        whereSQL += " and (" + query.getWhere_sql() + ") ";
                    } else {
                        whereSQL =  " where (" + query.getWhere_sql() + ") ";
                    }
                }
            }
        }
        //数据权限

        //页面传来排序
        if(requestMap.containsKey("sort")){
            String sort=(String) requestMap.get("sort") ;
            if(StringUtils.isNotEmpty(sort)) {
                String newsort=MemCache._columnsMap.get(funId+"."+sort);
                orderBySQL =" order by "+ newsort+" "+requestMap.get("order");
            }
        }

        //无设置条数，最多支持1000条结果
        if(!requestMap.containsKey("limit")){
            requestMap.put("limit",1000);
            requestMap.put("offset",0);
        }


        String dbSource=funInfo.getDb_source();
        if(StringUtils.isEmpty(dbSource)){
            dbSource=DataSourceNames.DEFAULT;
        }
        SqlSession session =getSession(dbSource);
        SqlMapper sqlMapper=new SqlMapper(session);
        List<String> keyList=new ArrayList<String>();
        try {
            requestMap=addDefaultValue2Map(requestMap);
            List<Map<String, Object>> rowList = sqlMapper.selectList(SQLPageHelper.SqlPage(getDBType(dbSource), selectSQL + fromSQL + whereSQL, orderBySQL), requestMap);
            //页面替换，如果基于数据传到前端涉及安全考虑可以在这里处理
            List<Map<String, Object>> newrowList=new ArrayList<Map<String, Object>>();
            for(Map<String, Object> row:rowList){
//                if(row.containsKey("dept_id")&&!row.containsKey("dept_name")){//增加dept_name列值
//
//                }
//                if(row.containsKey("user_id")&&!row.containsKey("user_name")){//增加dept_name列值
//
//                }
                Map<String, Object> newRow=new HashMap<String, Object>();
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    newRow.put( entry.getKey().toLowerCase(),entry.getValue());
                }
               if(newRow.containsKey(getTableKeyIdColumn(tableName))) keyList.add((String) newRow.get(getTableKeyIdColumn(tableName)));//结果集的所有主键值，用于判断是否存在附件
                newrowList.add(newRow);
            }

            Map<String, Object> cntMp = sqlMapper.selectOne(SQLPageHelper.CountSqlPage(getDBType(dbSource), "select 1 " + fromSQL + whereSQL), requestMap);
            //  log.info("rowList size():"+rowList.size());
            // log.info("row  count:"+cntMp.get("cnt"));
//            for (Map.Entry<String, Object> entry : cntMp.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }
            if(keyList!=null&&keyList.size()>0) {
                DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
                Example example = new Example(SystemAttachment.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("data_id", keyList);
                List<SystemAttachment> fileList = systemAttachmentMapper.selectByExample(example);
                if(fileList!=null&&fileList.size()>0){
//                    List files=new ArrayList();
//                    for(int i=0;i<fileList.size();i++){
//                        Map mp=new HashMap();
//                        mp.put("data_id",fileList.get(i).getData_id());
//                        mp.put("file_id",fileList.get(i).getFile_id());
//                        mp.put("file_path",fileList.get(i).getFile_path());
//                        files.add(mp);
//                    }
//                    resultSet.setFiles(files);
                    //在每行数据后面添加一个 system_row_fileid 字段
                    for(Map<String, Object> newRow:newrowList){
                        String fileIds="";
                        for(int i=0;i<fileList.size();i++){
                            if(newRow.containsKey(getTableKeyIdColumn(tableName)))
                                if(newRow.get(getTableKeyIdColumn(tableName)).equals(fileList.get(i).getData_id())){
                                    fileIds=fileIds+fileList.get(i).getFile_id()+";";
                                }
                        }
                        if(fileIds.indexOf(";")>-1) fileIds=fileIds.substring(0,fileIds.length()-1);
                        newRow.put("system_row_fileid",fileIds);
                    }
                }
                DynamicDataSource.clearDataSource();
            }
            resultSet.setRows(newrowList);
            resultSet.setTotal(Integer.parseInt(String.valueOf(cntMp.get("cnt")==null?cntMp.get("CNT"):cntMp.get("cnt"))));
        }catch (Exception e){
            log.error("查询sql出错！",e);
            throw new Exception(e);
        }finally {
            session.close();
        }
        return resultSet;
    }


    /**
     *  通过SQL查询单条记录
     * @param funId
     * @param requestMap,必须含有DataId值
     * @return
     */
    public Map<String,Object>  findBySQLRow(String funId, Map<String, Object> requestMap) {
        if(requestMap==null||requestMap.isEmpty()||!requestMap.containsKey("DataId")) return null;
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        if(funInfo==null){
            log.error("获取功能“"+funId+"”信息失败！请联系管理员！");
            return null;
        }
        String tableName=funInfo.getTable_name();
        String selectSQL=MemCache._selectSQLMap.get(funId);
        if(selectSQL==null){
            StringBuffer selectSQLsb=new StringBuffer("select ");
            List<ToolFunctionColumn> columns=layoutService.getFunctionColumn(funId);
            for(ToolFunctionColumn column:columns){
                if(!"sql".equals(column.getGroup_name()) ) continue;
                selectSQLsb.append(column.getColumn_statement()).append(",");
                MemCache._columnsMap.put(funId+"."+column.getColumn_name(),column.getColumn_statement());
            }
            selectSQL=selectSQLsb.substring(0,selectSQLsb.length()-1)+" ";
            if ( MemCache.getSystemParameter("selectSQLCache").equals("true")) {//是否查询字段缓存
                MemCache._selectSQLMap.put(funId,selectSQL);//存到缓存
            }
        }
        String fromSQL=funInfo.getFrom_sql();
        String whereSQL=funInfo.getWhere_sql();
        String keyColumn=funInfo.getKey_column();
        if(StringUtils.isNotEmpty(whereSQL)){
            whereSQL = " where " + whereSQL;
        }else{
            whereSQL="";
        }
        //增加主键条件
        if(StringUtils.isEmpty(keyColumn)){
            keyColumn=layoutService.getTableKeyIdColumn(tableName);
        }
        if(keyColumn.indexOf(".")<0){
            keyColumn=tableName+"."+keyColumn;
        }
        if (whereSQL.length() > 0) {
            whereSQL += " and (" + keyColumn+"=#{DataId} ) ";
        } else {
            whereSQL =  " where (" + keyColumn+"=#{DataId} ) ";
        }

        String dbSource=funInfo.getDb_source();
        if(StringUtils.isEmpty(dbSource)){
            dbSource=DataSourceNames.DEFAULT;
        }
        SqlSession session =getSession(dbSource);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            requestMap=addDefaultValue2Map(requestMap);
            List<Map<String, Object>> rowList = sqlMapper.selectList( selectSQL + fromSQL + whereSQL, requestMap);
            //页面替换，如果基于数据传到前端涉及安全考虑可以在这里处理
            if(rowList==null||rowList.isEmpty()) return null;
             Map<String, Object> row=rowList.get(0);
            Map<String, Object> newRow=new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                newRow.put( entry.getKey().toLowerCase(),entry.getValue());
            }
            return newRow;
        }catch (Exception e){
            log.error("查询sql出错！",e);
            return null;
        }finally {
            session.close();
        }
    }


}
