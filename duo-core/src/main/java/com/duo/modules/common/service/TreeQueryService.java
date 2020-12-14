package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.TreeBean;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.SQLFormatUtil;
import com.duo.core.utils.StringUtils;
import com.duo.modules.tool.entity.ToolFunction;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TreeQueryService extends BaseService{
    @Autowired
    private LayoutService layoutService;

    /**
     * 获取树数据
     * @param pId
     * @return
     * @throws Exception
     */
    public List<TreeBean>  findTree(String tableName,String pId,String funId,String dbSource) throws Exception {
        log.info("TreeService :: tableName="+tableName);
        //父id是否为空
        if (StringUtils.isEmpty(pId)) {
            pId = "";
        }
        String parentID=pId;
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String treeWhereSQL="";
        String treeOrderBySQL="";
        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTree_wheresql())) {
            treeWhereSQL=funInfo.getTree_wheresql();

        }

        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTree_orderbysql())) {
            treeOrderBySQL=funInfo.getTree_orderbysql();

        }
        tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        String keycolumn= getTableKeyIdColumn(tableName);//主键列
        dbSource=dbSource==null?(funInfo==null?DataSourceNames.DEFAULT:funInfo.getDb_source()):dbSource;
        //获取数据源sqlsession
        SqlSession session =getSession(dbSource);
        SqlMapper sqlMapper=new SqlMapper(session);
        List<Map<String, Object>> rowList;
        List<Map<String, Object>> subrowList;
        try {
            //4位一级，查询下级数据
            parentID = parentID + "____";
           rowList = sqlMapper.selectList("select * from " + tableName + " where " + keycolumn + " like #{parentID}" + (StringUtils.isEmpty(treeWhereSQL) ? "" : " and (" + treeWhereSQL + ")")+" "+treeOrderBySQL, parentID);
            log.info("select * from " + tableName + " where " + keycolumn + " like #{parentID}" + (StringUtils.isEmpty(treeWhereSQL) ? "" : " and (" + treeWhereSQL + ")")+" "+treeOrderBySQL);
            if (rowList == null || rowList.size() == 0) {
                return null;
            }
            //看看下下级是否有数据，避免频繁查询，一次将有下级数据的子集取出
            parentID = parentID + "____";
            subrowList = sqlMapper.selectList(SQLFormatUtil.formatSQL(getDBType(dbSource),
                    "select distinct {SUBSTR}(" + keycolumn + ",1," + (pId.length() + 4) + ") as " + keycolumn + " from " + tableName + " where " + keycolumn + " like #{parentID}"+" "+treeOrderBySQL), parentID);
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
        Map<String,String> hasSubMp=new HashMap<>();
        for(Map<String,Object> mp:subrowList){
            hasSubMp.put(String.valueOf(mp.get(keycolumn)),"true");
        }
        //通过创建实体获取对象设置的显示text字段名
        Class clazz=MemCache._entitys.get(tableName);
        BaseEntity obj =(BaseEntity) clazz.newInstance();
        //显示字段名
        String textColumn=obj.getTextColumn();

        //拼装树型数据对象
        List<TreeBean> result=new ArrayList<TreeBean>() ;
        for(Map<String,Object> mp:rowList){
            //去掉公共部分信息
            mp.remove("add_date");
            mp.remove("add_userid");
            mp.remove("modify_date");
            mp.remove("modify_userid");
            mp.remove("record_flag");
            mp.remove("memo");
            TreeBean bean=new TreeBean();
            bean.setObj(mp);
            //根据设置来取 真实值
            String id=String.valueOf(mp.get(keycolumn));
            bean.setId(id);
            bean.setPId(pId);
            //根据设置来取 显示值
            if(StringUtils.isEmpty(textColumn)){
                bean.setName(id);
            }else if(textColumn.indexOf(",")>0) {
                String[] columns=textColumn.split(",");
                bean.setName(String.valueOf(mp.get(columns[0])) + "(" + mp.get(columns[1]) + ")"+("tool_table".equals(tableName)&&mp.get("is_tree").equals("1")?"\uD83C\uDF33":""));
            }else{
                bean.setName(String.valueOf(mp.get(textColumn)));
            }
            bean.setParent(hasSubMp.containsKey(id));
            result.add(bean);
        }
        return result;
    }


    /**
     * 获取树数据
     * @param pId
     * @return
     * @throws Exception
     */
    public List<TreeBean>  findNormalTree(String tableName,String pId,String funId) throws Exception {
        log.info("TreeService :: tableName="+tableName);
        //父id是否为空
        if (StringUtils.isEmpty(pId)) {
            pId = "";
        }
        String parentID=pId;
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String treeWhereSQL="";
        String treeOrderBySQL="";
        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTree_wheresql())) {
            treeWhereSQL=funInfo.getTree_wheresql();

        }

        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTree_orderbysql())) {
            treeOrderBySQL=funInfo.getTree_orderbysql();

        }
        tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        String keycolumn= getTableKeyIdColumn(tableName);//主键列

        //获取数据源sqlsession
        SqlSession session =getSession(funInfo==null?DataSourceNames.DEFAULT:funInfo.getDb_source());
        SqlMapper sqlMapper=new SqlMapper(session);
        List<Map<String, Object>> rowList;
        List<Map<String, Object>> subrowList;
        try {
            //4位一级，查询下级数据
            parentID = parentID + "%";
            rowList = sqlMapper.selectList("select * from " + tableName + " where " + keycolumn + " like #{parentID}" + (StringUtils.isEmpty(treeWhereSQL) ? "" : " and (" + treeWhereSQL + ")")+" "+treeOrderBySQL, parentID);
            log.info("select * from " + tableName + " where " + keycolumn + " like #{parentID}" + (StringUtils.isEmpty(treeWhereSQL) ? "" : " and (" + treeWhereSQL + ")")+" "+treeOrderBySQL);
            if (rowList == null || rowList.size() == 0) {
                return null;
            }   }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
        //通过创建实体获取对象设置的显示text字段名
        Class clazz=MemCache._entitys.get(tableName);
        BaseEntity obj =(BaseEntity) clazz.newInstance();
        //显示字段名
        String textColumn=obj.getTextColumn();

        //拼装树型数据对象
        List<TreeBean> result=new ArrayList<TreeBean>() ;
        for(Map<String,Object> mp:rowList){
            //去掉公共部分信息
            mp.remove("add_date");
            mp.remove("add_userid");
            mp.remove("modify_date");
            mp.remove("modify_userid");
            mp.remove("record_flag");
            mp.remove("memo");
            TreeBean bean=new TreeBean();
            bean.setObj(mp);
            //根据设置来取 真实值
            String id=String.valueOf(mp.get(keycolumn));
            bean.setId(id);
            bean.setPId(pId);
            //根据设置来取 显示值
            if(StringUtils.isEmpty(textColumn)){
                bean.setName(id);
            }else if(textColumn.indexOf(",")>0) {
                String[] columns=textColumn.split(",");
                bean.setName(String.valueOf(mp.get(columns[0])) + "(" + mp.get(columns[1]) + ")");
            }else{
                bean.setName(String.valueOf(mp.get(textColumn)));
            }
            bean.setParent(false);
            result.add(bean);
        }
        return result;
    }

}
