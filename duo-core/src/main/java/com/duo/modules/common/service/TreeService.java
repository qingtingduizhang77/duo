package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.*;
import com.duo.core.utils.*;
import com.duo.modules.tool.entity.ToolFunction;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
@Scope("prototype") //设置多例模式
public class TreeService extends BaseService{
    @Autowired
    private LayoutService layoutService;


    /**
     * 新增数据
     * @param requestMap
     * @throws Exception
     */
    public void create( Map<String, Object> requestMap)throws Exception {
        //判断上级id不能为空
        Object pid= requestMap.get("parentId") ;
        if(pid==null){
            new Throwable("parentId is null!");
        }

        String tableName="tool_table";//操作表名
        tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        String keycolumn= getTableKeyIdColumn(tableName);//主键列

        //获取树型结构id
        String newid=this.getTreeID(DataSourceNames.DEFAULT,tableName,pid);
        requestMap.put(keycolumn,newid);
        //根据功能配置，对页面无赋值字段设置默认值
        requestMap=addDefaultValue2Map(requestMap);

        //通过页面参数创建对象
        Class clazz=MemCache._entitys.get(tableName);
        BaseEntity entity=(BaseEntity)Map2EntityUtil.createModel(clazz,requestMap);

        Map fullNameMp=new HashMap();
        //更新全称字段值
        log.info("FullNameColumn::"+entity.getFullNameColumn());
        if(!StringUtils.isEmpty(entity.getFullNameColumn())){
            if(newid.length()>4) {
                String fullname="";
                for(int i=0;i*4<newid.length()-4;i++) {
                    String fatherId = newid.substring(0,  4+i*4);
                    BaseEntity fentity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(fatherId);
                   if(fentity!=null) fullname=fullname+(fullname.length()>1?"-":"")+E2MapUtil.getEValueByKey(fentity,getEntity(tableName),entity.getTextColumn());
                }
                fullNameMp.put(entity.getFullNameColumn(),fullname+"-"+E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
            }else{
                fullNameMp.put(entity.getFullNameColumn(),E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
            }
        }
       if(!fullNameMp.isEmpty()) entity= Map2EntityUtil.updateModel(entity,fullNameMp);

        //级别字段赋值
        entity.setLevel(newid.length()/4);
        entity=addDataInfo(entity);
        //打印
        E2MapUtil.pringEntity(entity,clazz);
        //插入记录
        getMapper(tableName).insertSelective(entity);
    }

    /**
     * 更新数据
     * @param requestMap
     * @throws Exception
     */
    public void update( Map<String, Object> requestMap)throws Exception {
        String tableName="tool_table";//操作表名
        //获取主键id
        String keyid=(String)requestMap.get(getTableKeyIdColumn(tableName));

        if(StringUtils.isEmpty(keyid)){
            new Throwable("KeyId is null!");
        }
        //从数据库拿出对象
        BaseEntity entity= (BaseEntity)getMapper(tableName).selectByPrimaryKey(keyid);

        //根据功能配置，对页面无赋值字段设置默认值
        requestMap=addDefaultValue2Map(requestMap);
        //根据页面最新值更新对象//初始化对象
        entity=(BaseEntity)Map2EntityUtil.updateModel(entity,requestMap);
        entity=modifyDataInfo(entity);
        //更新全称字段值
        log.info("FullNameColumn::"+entity.getFullNameColumn());
        if(!StringUtils.isEmpty(entity.getFullNameColumn())){
            Map fullNameMp=new HashMap();
            if(keyid.length()>4) {
                String fullname="";
                for(int i=0;i*4<keyid.length()-4;i++) {
                    String fatherId = keyid.substring(0,  4+i*4);
                    BaseEntity fentity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(fatherId);
                    fullname=fullname+(fullname.length()>1?"-":"")+E2MapUtil.getEValueByKey(fentity,getEntity(tableName),entity.getTextColumn());
                }
                fullNameMp.put(entity.getFullNameColumn(),fullname+"-"+E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
            }else{
                fullNameMp.put(entity.getFullNameColumn(),E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
            }
            if(!fullNameMp.isEmpty()) entity = (BaseEntity) Map2EntityUtil.updateModel(entity, fullNameMp);
        }
        //打印
        Class clazz= MemCache._entitys.get(tableName);
        E2MapUtil.pringEntity(entity,clazz);
        //保存记录
        getMapper(tableName).updateByPrimaryKeySelective(entity);
    }

    /**
     * 删除数据
     * @param requestMap
     * @param id
     */
    public void delete( Map<String, Object> requestMap,String id){
        if(StringUtils.isEmpty(id)){
            new Throwable("KeyId is null!");
        }

        String tableName= "tool_table";//操作表名
        tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        String keycolumn= getTableKeyIdColumn(tableName);//主键列

        //获取数据源sqlsession
        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            //通过删除事件附加属性，删除关联表

            //删除记录及子记录
            id = id + "%";//删除自身及子数据
            sqlMapper.delete("delete from " + tableName + " where " + keycolumn + " like #{id}", id);
        }catch (Exception e){
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 标记性删除
     * @param requestMap
     * @param id
     */
    public void flagDelete( Map<String, Object> requestMap,String id){
        if(StringUtils.isEmpty(id)){
            new Throwable("KeyId is null!");
        }

        String tableName= "tool_table";//操作表名
        tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        String keycolumn= getTableKeyIdColumn(tableName);//主键列

        //获取数据源sqlsession
        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            //通过删除事件附加属性，删除关联表

            //删除记录及子记录
            id=id+"%";//删除自身及子数据
            sqlMapper.delete("update record_flag='9' from "+tableName+" where "+keycolumn+" like #{id}",id);
        }catch (Exception e){
            throw  e;
        }finally {
            session.close();
        }
    }



    /**
     * 删除数据
     * @param funId
     * @param ids
     */
//    @Transactional(rollbackFor=Exception.class)
    public void deletes(String funId,Object... ids) {
        log.info( "TreeService delete:"+funId);
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String tableName="";
        String dbSource=DataSourceNames.DEFAULT;
        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTable_name())) {
            tableName = funInfo.getTable_name().trim();
            tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
            String keycolumn= getTableKeyIdColumn(tableName);//主键列
            if(StringUtils.isNotEmpty(funInfo.getDb_source())){
                dbSource=funInfo.getDb_source().trim();
            }
            //获取数据源sqlsession
            SqlSession session =getSession(dbSource);
            SqlMapper sqlMapper=new SqlMapper(session);
            try {
                log.info("delete from " + tableName + " where " + keycolumn + " like #{id}");
                for (Object id : ids) {
                    //通过删除事件附加属性，删除关联表

                    //删除记录及子记录
                    id = id + "%";//删除自身及子数据
                    sqlMapper.delete("delete from " + tableName + " where " + keycolumn + " like #{id}", id);
                }
            }catch (Exception e){
                throw  e;
            }finally {
                session.close();
            }
        }
    }

    /**
     * gridcopy 批量复制
     * @param funId
     * @param keyids
     * @throws Exception
     */
//    @Transactional(rollbackFor=Exception.class)
    public void copy(String funId,String... keyids) throws Exception {
        String tableName="";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String dbSource=DataSourceNames.DEFAULT;
        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            tableName=funInfo.getTable_name().trim();
            if(StringUtils.isNotEmpty(funInfo.getDb_source())){
                dbSource=funInfo.getDb_source().trim();
            }
            DynamicDataSource.setDataSource(dbSource);//设置指定数据源
            log.info("set datasource is " + dbSource);
        }
        try {
            for (String keyid : keyids) {
                String pId=keyid.substring(0,keyid.length()-4);
                String newid =  getTreeID(dbSource,tableName,pId);
                log.info("TreeService copy:" + tableName + "  copyID:" + keyid + " newID:" + newid);
                //从数据库拿出对象
                BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                Map mp = new HashMap();
                mp.put(getTableKeyIdColumn(tableName), newid);
                //更新相关值
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, mp);
                entity.setModify_date(null);
                entity.setModify_userid("");
                entity = addDataInfo(entity);
                //根据功能配置，对页面无赋值字段设置默认值

                //打印
                Class clazz = MemCache._entitys.get(tableName);
                E2MapUtil.pringEntity(entity, clazz);
                getMapper(tableName).insertSelective(entity);
            }
        }catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
    }

    /**
     *  gridsave 批量保存
     * @param funId
     * @param list
     * @throws Exception
     */
//    @Transactional(rollbackFor=Exception.class)
    public void gridSave(String funId, List<Map> list ) throws Exception {
        log.info("TreeService gridSave:" + funId  );
        String tableName="";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String dbSource=DataSourceNames.DEFAULT;
        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            tableName=funInfo.getTable_name().trim();
            tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
            if(StringUtils.isNotEmpty(funInfo.getDb_source())){
                dbSource=funInfo.getDb_source().trim();
            }
            DynamicDataSource.setDataSource(dbSource);//设置指定数据源
            log.info("set datasource is " + dbSource);
        }
        try {
            String keycolumn = getTableKeyIdColumn(tableName);//主键列
            log.info("keycolumn=========="+keycolumn);
            for (Map mp : list) {
                String keyid = (String) mp.get(keycolumn);
                Object pId =  mp.get("pId");
                if (StringUtils.isEmpty(keyid)) {//新增数据
                    //获取树型结构id
                    String newid = getTreeID(dbSource,tableName,pId);
                    mp.put(keycolumn, newid);
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);

                    //通过页面参数创建对象
                    Class clazz = MemCache._entitys.get(tableName);
                    BaseEntity entity = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
                    //级别字段赋值
                    entity.setLevel(newid.length() / 4);
                    entity = addDataInfo(entity);

                    //更新全称字段值
                    log.info("FullNameColumn::"+entity.getFullNameColumn());
                    if(!StringUtils.isEmpty(entity.getFullNameColumn())){
                        Map fullNameMp=new HashMap();
                        if(newid.length()>4) {
                            String fullname="";
                            for(int i=0;i*4<newid.length()-4;i++) {
                                String fatherId = newid.substring(0,  4+i*4);
                                BaseEntity fentity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(fatherId);
                                fullname=fullname+(fullname.length()>1?"-":"")+E2MapUtil.getEValueByKey(fentity,getEntity(tableName),entity.getTextColumn());
                            }
                            fullNameMp.put(entity.getFullNameColumn(),fullname+"-"+E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
                        }else{
                            fullNameMp.put(entity.getFullNameColumn(),E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
                        }
                       if(!fullNameMp.isEmpty()) entity = (BaseEntity) Map2EntityUtil.updateModel(entity, fullNameMp);
                    }

                    //打印
                    E2MapUtil.pringEntity(entity, clazz);
                    //插入记录
                    getMapper(tableName).insertSelective(entity);
                } else {//修改数据
                    if (StringUtils.isEmpty(keyid)) {
                        new Throwable("KeyId is null!");
                    }
                    //从数据库拿出对象
                    BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                    //根据功能配置，对页面无赋值字段设置默认值
                    mp=addDefaultValue2Map(mp);

                    //根据页面最新值更新对象//初始化对象
                    entity = (BaseEntity) Map2EntityUtil.updateModel(entity, mp);
                    //更新全称字段值
                    log.info("FullNameColumn::"+entity.getFullNameColumn());
                    if(!StringUtils.isEmpty(entity.getFullNameColumn())){
                        Map fullNameMp=new HashMap();
                        if(keyid.length()>4) {
                            String fullname="";
                            for(int i=0;i*4<keyid.length()-4;i++) {
                                String fatherId = keyid.substring(0,  4+i*4);
                                BaseEntity fentity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(fatherId);
                                fullname=fullname+(fullname.length()>1?"-":"")+E2MapUtil.getEValueByKey(fentity,getEntity(tableName),entity.getTextColumn());
                            }
                            fullNameMp.put(entity.getFullNameColumn(),fullname+"-"+E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
                        }else{
                            fullNameMp.put(entity.getFullNameColumn(),E2MapUtil.getEValueByKey(entity,getEntity(tableName),entity.getTextColumn()));
                        }
                        if(!fullNameMp.isEmpty()) entity = (BaseEntity) Map2EntityUtil.updateModel(entity, fullNameMp);
                    }

                    entity = modifyDataInfo(entity);
                    //打印
                    Class clazz = MemCache._entitys.get(tableName);
                    E2MapUtil.pringEntity(entity, clazz);
                    //保存记录
                    getMapper(tableName).updateByPrimaryKeySelective(entity);
                }

            }
        }catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
    }
}
