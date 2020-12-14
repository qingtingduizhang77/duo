package com.duo.modules.common.service;

import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.*;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.tool.entity.ToolFunction;
import com.duo.modules.tool.entity.ToolFunctionColumn;
import com.duo.modules.tool.entity.ToolFunctionEventSql;
import com.duo.modules.tool.mapper.ToolFunctionEventSqlMapper;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
@Scope("prototype") //设置多例模式
public class CommonService extends BaseService{
    //在配置文件中配置的文件保存路径
//    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ToolFunctionEventSqlMapper toolFunctionEventSqlMapper;

    /**
     * griddelete 事件批量删除
     * @param funId
     * @param ids
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?>  delete(String funId,Object[] ids,Map<String, Object> requestMap) throws Exception {
        log.info( "CommonService delete:"+funId);
        String tableName="tool_table_column";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);

        //查询事件后触发内容
        String eventId=(String)requestMap.get("eventId");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        if(StringUtils.isEmpty(eventId)) eventId="zzzZZZ";
        Example exampleEvent = new Example(ToolFunctionEventSql.class);
        exampleEvent.setOrderByClause("order_index");
        Example.Criteria criteriaEvent = exampleEvent.createCriteria();
        criteriaEvent.andEqualTo("event_id",eventId);
        criteriaEvent.andEqualTo("source_funid",funId);
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(exampleEvent);

        String dbSource=DataSourceNames.DEFAULT;
        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            tableName=funInfo.getTable_name().trim();
            if(StringUtils.isNotEmpty(funInfo.getDb_source())){
                dbSource=funInfo.getDb_source().trim();
            }
        }
        String deleteSubFuns=(String)requestMap.get("deleteSubFuns");
        log.info("deleteSubFuns:" + deleteSubFuns );

        try {
            for (Object id : ids) {
                if(id.toString().equals("")) continue;
                log.info( "CommonService delete id===:"+id);
                //根据功能配置参数先删除子表
                if(StringUtils.isNotEmpty(deleteSubFuns)&&deleteSubFuns.length()>0) {
                    for (String subFunid : deleteSubFuns.split(";")) {

                        //；sunfunid[subsubfunid;...;]；
                        String subsubfunids="";
//                        log.info("delete  subFunid============"+subFunid);
                        if(subFunid.indexOf("[")>0){
                            subsubfunids=subFunid.split("\\[")[1].split("\\]")[0];
                            subFunid=subFunid.split("\\[")[0];
                        }
//                        log.info("delete  subsubfunids============"+subsubfunids);
                        //获取功能设置信息
                        //避免造成混乱，数据源不切换，跟主表数据源一致，不一致的自己写代码实现复制
                        ToolFunction subfunInfo=layoutService.getFunctionInfo(subFunid);
                        String subTableName=subfunInfo.getTable_name();
                        String foreignKeyColumn=StringUtils.emptyToDefault(subfunInfo.getForeign_column(),getTableKeyIdColumn(tableName));

                        Class clazz = MemCache._entitys.get(subTableName);
                        Map mp = new HashMap();
                        mp.put(foreignKeyColumn, id);
                        BaseEntity record = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
                        String dbSourceSub=DataSourceNames.DEFAULT;
                        if(StringUtils.isNotEmpty(subfunInfo.getDb_source())){
                            dbSourceSub=subfunInfo.getDb_source().trim();
                        }

                        //支持三级删除
                        if(StringUtils.isNotEmpty(subsubfunids)&&subsubfunids.length()>0) {
                            for (String subsubFunid : subsubfunids.split(";")) {
                                //获取功能设置信息
                                //避免造成混乱，数据源不切换，跟主表数据源一致，不一致的自己写代码实现复制
                                ToolFunction subsubfunInfo=layoutService.getFunctionInfo(subsubFunid);
                                String subsubTableName=subsubfunInfo.getTable_name();
                                String subforeignKeyColumn=StringUtils.emptyToDefault(subsubfunInfo.getForeign_column(),getTableKeyIdColumn(subTableName));

                                Class subclazz = MemCache._entitys.get(subsubTableName);
                                Map submp = new HashMap();
                                submp.put(subforeignKeyColumn, id);
                                BaseEntity subrecord = (BaseEntity) Map2EntityUtil.createModel(subclazz, submp);
                                String subdbSourceSub=DataSourceNames.DEFAULT;
                                if(StringUtils.isNotEmpty(subsubfunInfo.getDb_source())){
                                    subdbSourceSub=subsubfunInfo.getDb_source().trim();
                                }
                                log.info("set datasource is " + subdbSourceSub);
                                DynamicDataSource.setDataSource(subdbSourceSub);//设置指定数据源
                                getMapper(subsubTableName).delete(subrecord);
                                DynamicDataSource.clearDataSource();//清理
                            }
                        }

                        log.info("set datasource is " + dbSourceSub);
                        DynamicDataSource.setDataSource(dbSourceSub);//设置指定数据源
                        getMapper(subTableName).delete(record);
                        DynamicDataSource.clearDataSource();//清理
                    }
                }
                log.info("set datasource is " + dbSource);
                DynamicDataSource.setDataSource(dbSource);//设置指定数据源
                getMapper(tableName).deleteByPrimaryKey(id);
                DynamicDataSource.clearDataSource();//清理
                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, String.valueOf(id), foreignKeyId,"delete");
                    if (!r.isSuccess()) return r;
                }
            }
        } catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
        return  Result.success();
    }

    /**
     * gridcopy 批量复制
     * @param funId
     * @param keyids
     * @throws Exception
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?> copy(String funId,String[] keyids,Map<String, Object> requestMap) throws Exception {
        log.info("CommonService copy start!"  );
        String tableName="tool_table_column";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        List<ToolFunctionColumn> AutoCodeColumns =layoutService.getFunctionAutoColumn(funId);//自动编码判断
        String dbSource=DataSourceNames.DEFAULT;

        if(StringUtils.isNotEmpty(funInfo.getDb_source())){
            dbSource=funInfo.getDb_source().trim();
        }
        //查询事件后触发内容
        String eventId=(String)requestMap.get("eventId");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        if(StringUtils.isEmpty(eventId)) eventId="zzzZZZ";
        Example exampleEvent = new Example(ToolFunctionEventSql.class);
        exampleEvent.setOrderByClause("order_index");
        Example.Criteria criteriaEvent = exampleEvent.createCriteria();
        criteriaEvent.andEqualTo("event_id",eventId);
        criteriaEvent.andEqualTo("source_funid",funId);
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(exampleEvent);

        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            tableName=funInfo.getTable_name().trim();
        }
        String copySubFuns=(String)requestMap.get("copySubFuns");
        log.info("copySubFuns:" + copySubFuns );
        try {
            for (String keyid : keyids) {
                if(keyid.equals("")) continue;
                String newid = getKeyUID() ;
                if(keyids.length==1){
                    newid=StringUtils.emptyToDefault((String)requestMap.get("newID"),newid);//如果已经指定了主键值，取指定值，仅支持一条数据的复制
                    log.info("newID===="+newid);
                    DynamicDataSource.setDataSource(dbSource);//设置指定数据源
                    BaseEntity newentity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(newid);
                    DynamicDataSource.clearDataSource();
                    if(newentity!=null){
                        return Result.failure("500","该主键ID已存在，复制失败！");
                    }
                }
                log.info("CommonService copy:" + tableName + "  copyID:" + keyid + " newID:" + newid);
                //从数据库拿出对象
                DynamicDataSource.setDataSource(dbSource);//设置指定数据源
                BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                DynamicDataSource.clearDataSource();
                Map mp = new HashMap();
                mp.put(getTableKeyIdColumn(tableName), newid);
                //复核字段归0
                if(StringUtils.isNotEmpty(funInfo.getAudit_column())){
                    mp.put(funInfo.getAudit_column(),"0");
                }

                //自动编码
                if(AutoCodeColumns!=null&&AutoCodeColumns.size()>0){
                    for(ToolFunctionColumn codecolumn:AutoCodeColumns){
                        String code= (String) mp.get(codecolumn.getColumn_name());
                        if(StringUtils.isNotEmpty(code)) continue;
                        code=autoRuleCode(tableName,dbSource,codecolumn.getAuto_code(),codecolumn.getCompute_sql(),mp,codecolumn.getCode_length(),codecolumn.getPre_code());
                        mp.put(codecolumn.getColumn_name(),code);
                    }
                }

                //更新相关值
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, mp);
                entity.setModify_date(null);
                entity.setModify_userid("");
                entity = addDataInfo(entity);
                //打印
                Class clazz = MemCache._entitys.get(tableName);
                E2MapUtil.pringEntity(entity, clazz);
                DynamicDataSource.setDataSource(dbSource);//设置指定数据源
                getMapper(tableName).insertSelective(entity);
                DynamicDataSource.clearDataSource();

                //根据功能配置参数复制子功能
                if(StringUtils.isNotEmpty(copySubFuns)&&copySubFuns.length()>0){
                    for(String subFunid:copySubFuns.split(";")){
                        //；sunfunid[subsubfunid;...;]；
                        String subsubfunids="";
//                        log.info("copy  subFunid============"+subFunid);
                        if(subFunid.indexOf("[")>0){
                            subsubfunids=subFunid.split("\\[")[1].split("\\]")[0];
                            subFunid=subFunid.split("\\[")[0];
                        }
//                        log.info("copy  subsubfunids============"+subsubfunids);
                        //获取功能设置信息
                        //避免造成混乱，数据源不切换，跟主表数据源一致，不一致的自己写代码实现复制
                        ToolFunction subfunInfo=layoutService.getFunctionInfo(subFunid);
                        String subTableName=subfunInfo.getTable_name();
                        String foreignKeyColumn=StringUtils.emptyToDefault(subfunInfo.getForeign_column(),getTableKeyIdColumn(tableName));

                        Example example = new Example(getEntity(subTableName));
                        example.setOrderByClause(getTableKeyIdColumn(subTableName));
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andEqualTo(foreignKeyColumn,keyid);
                        String dbSourceSub=DataSourceNames.DEFAULT;
                        if(StringUtils.isNotEmpty(subfunInfo.getDb_source())){
                            dbSourceSub=subfunInfo.getDb_source().trim();
                        }
                        DynamicDataSource.setDataSource(dbSourceSub);//设置指定数据源
                        List<BaseEntity> subdataList=getMapper(subTableName).selectByExample(example);
                        DynamicDataSource.clearDataSource();
                        for(BaseEntity subdata:subdataList){
                            String subKeyid= (String) E2MapUtil.getEValueByKey(subdata,getEntity(subTableName),getTableKeyIdColumn(subTableName));
                            Map submp = new HashMap();
                            String subnewid=getUUID();
                            submp.put(getTableKeyIdColumn(subTableName), subnewid);
                            submp.put(foreignKeyColumn, newid);
                            //更新相关值
                            subdata = (BaseEntity) Map2EntityUtil.updateModel(subdata, submp);
                            subdata.setModify_date(null);
                            subdata.setModify_userid("");
                            subdata = addDataInfo(subdata);

                            DynamicDataSource.setDataSource(dbSourceSub);//设置指定数据源
                            getMapper(subTableName).insertSelective(subdata);
                            DynamicDataSource.clearDataSource();
                            //最多支持3层子功能
                            if(StringUtils.isNotEmpty(subsubfunids)) {
                                //根据功能配置参数复制子功能
                                if (subsubfunids.length() > 0) {
                                    for (String subsubFunid : subsubfunids.split(";")) {
                                        //获取功能设置信息
                                        //避免造成混乱，数据源不切换，跟主表数据源一致，不一致的自己写代码实现复制
                                        ToolFunction subsubfunInfo = layoutService.getFunctionInfo(subsubFunid);
                                        String subsubTableName = subsubfunInfo.getTable_name();
                                        String subforeignKeyColumn=StringUtils.emptyToDefault(subsubfunInfo.getForeign_column(),getTableKeyIdColumn(subTableName));
                                        Example subexample = new Example(getEntity(subsubTableName));
                                        example.setOrderByClause(getTableKeyIdColumn(subsubTableName));
                                        Example.Criteria subcriteria = subexample.createCriteria();
                                        subcriteria.andEqualTo(subforeignKeyColumn, subKeyid);

                                        String subdbSourceSub=DataSourceNames.DEFAULT;
                                        if(StringUtils.isNotEmpty(subsubfunInfo.getDb_source())){
                                            subdbSourceSub=subsubfunInfo.getDb_source().trim();
                                        }
                                        DynamicDataSource.setDataSource(subdbSourceSub);//设置指定数据源
                                        List<BaseEntity> subsubdataList = getMapper(subsubTableName).selectByExample(subexample);
                                        for (BaseEntity subsubdata : subsubdataList) {
                                            Map subsubmp = new HashMap();
                                            subsubmp.put(getTableKeyIdColumn(subTableName), subnewid);//有可能第一层的外键也要存
                                            subsubmp.put(getTableKeyIdColumn(subsubTableName), getUUID());
                                            subsubmp.put(subforeignKeyColumn, subnewid);
                                            //更新相关值
                                            subsubdata = (BaseEntity) Map2EntityUtil.updateModel(subsubdata, subsubmp);
                                            subsubdata.setModify_date(null);
                                            subsubdata.setModify_userid("");
                                            subsubdata = addDataInfo(subsubdata);
                                            getMapper(subsubTableName).insertSelective(subsubdata);
                                        }
                                        DynamicDataSource.clearDataSource();
                                    }
                                }
                            }
                        }
                    }
                }
                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,"copy");
                    if (!r.isSuccess()) return r;
                }
            }
            return  Result.success();
        }catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
    }

    /**
     *   save  保存
     * @param funId
     * @param requestMap
     * @throws Exception
     */
    public Result<?> saveFile(Map<String, MultipartFile> fileMap, HttpServletRequest request, String funId, Map<String,Object>  requestMap ) throws Exception {
//      if(file!=null) {
//          //保存文件
//          ToolFunction funinfo = layoutService.getFunctionInfo(funId);
//          String tableName = funinfo != null ? funinfo.getTable_name() : "";
//          String url;
//          log.info("上传文件===" + "\n");
//          //判断文件是否为空
//          if (file.isEmpty()) {
//              new Throwable("上传文件不可为空");
//          }
//          // 获取文件名
//          String fileName = file.getOriginalFilename();
//          String fileType = "";
//          if (fileName.indexOf(".") > 0) fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
//          log.info("上传的文件名为: " + fileName + "\n");
//          String fileID = layoutService.getKeyUID();
//          String newfileName = funId + fileID;
//          log.info("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + newfileName + "\n");
//          //加个时间戳，尽量避免文件名称重复
//          String path = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + newfileName;//sqlprop.getFilePath()+
//          //文件绝对路径
//          log.info("保存文件绝对路径:" + path + "\n");
//
//          //创建文件路径
//          File dest = new File(path);
//          //判断文件是否已经存在
//          if (dest.exists()) {
//              log.error("文件已经存在");
//              new Throwable("文件已经存在");
//          }
//          //判断文件父目录是否存在
//          if (!dest.getParentFile().exists()) {
//              dest.getParentFile().mkdir();
//          }
//          String newid = getKeyUID();
//          try {
//              //上传文件
//              file.transferTo(dest); //保存文件
//              log.info("保存文件路径" + path + "\n");
//              SystemAttachment attachment = new SystemAttachment();
//              attachment.setFile_id(fileID);
//              attachment.setData_id(newid);
//              attachment.setFile_date(new Date());
//              attachment.setFile_name(fileName);
//              attachment.setFile_path(path);
//              attachment.setFile_size(FileUtils.getPrintSize(dest.length()));
//              attachment.setFile_md5(DigestUtils.md5Hex(new FileInputStream(dest)));
//              attachment.setFile_type(fileType.toLowerCase());
//              attachment.setDept_id(ShiroUtils.getDeptId());
//              attachment.setDept_name(ShiroUtils.getDeptName());
//              attachment.setTable_name(tableName);
//              attachment.setType_id("");
//              attachment.setUpload_ip(WebUtil.getIpAddress(request));
//              attachment.setUpload_user(ShiroUtils.getUserName());
//              layoutService.addDataInfo(attachment);
//              systemAttachmentMapper.insert(attachment);
//
//          } catch (IOException e) {
//              log.error("上传失败", e);
//              new Throwable(e);
//          }
//
//          String fileURL = "xxxx?fileId=" + fileID;
//          String fileColumn = (String) mp.get("fileColumn");
//          mp.put(fileColumn, fileURL);
//          mp.put("newId", newid);
//      }
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String tableName="";
        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            tableName=funInfo.getTable_name().trim();
            tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
        }
        String keycolumn = getTableKeyIdColumn(tableName);//主键列
        log.info("keycolumn=========="+keycolumn);
        String keyid = (String) requestMap.get(keycolumn);
        if(StringUtils.isEmpty(keyid)){//新增数据
            keyid=getKeyUID();
            requestMap.put(keycolumn,keyid);
        }
        if(fileMap!=null&&!fileMap.isEmpty()) {
            Result r = fileService.upload(fileMap, requestMap, WebUtil.getIpAddress(request));
            if (r.isSuccess()) {
                List<Map> ls = (List<Map>) r.getData();
                for (Map mp : ls) {
                    if (mp.containsKey("column_name")) {
                        requestMap.put((String) mp.get("column_name"), mp.get("file_id"));
                    }
                }

            }
        }
        //保存文字
        return save(funId, requestMap);
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?> save(String funId,  Map<String,Object>  mp ) throws Exception {
        log.info("CommonService save:" + funId  );
        String tableName="";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        List<ToolFunctionColumn> SingleColumns =layoutService.getFunctionSingleColumn(funId);//唯一性判断
        List<ToolFunctionColumn> AutoCodeColumns =layoutService.getFunctionAutoColumn(funId);//自动编码判断
        //查询事件后触发内容
        String eventId=(String)mp.get("eventId");
        String foreignKeyId=(String)mp.get("foreignKeyId");
        if(StringUtils.isEmpty(eventId)) eventId="zzzZZZ";
        Example example = new Example(ToolFunctionEventSql.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("event_id",eventId);
        criteria.andEqualTo("source_funid",funId);
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(example);

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
            String keyid = (String) mp.get(keycolumn);
            boolean isNew=true;
            BaseEntity entity=null;
            if (StringUtils.isNotEmpty(keyid)) {
                //从数据库拿出对象
                entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                if(entity!=null){
                    isNew=false;
                }
            }

            //判断唯一性
            if(SingleColumns!=null&&SingleColumns.size()>0){
                SqlSession session = getSession(dbSource);
                SqlMapper sqlMapper = new SqlMapper(session);
                try {
                    for (ToolFunctionColumn column : SingleColumns) {
                        //传入值和现在对象值一样，不再判断
                        if(entity!=null
                                &&E2MapUtil.getEValueByKey(entity,getEntity(tableName),column.getColumn_name()).equals(mp.get(column.getColumn_name()))){
                             continue;
                        }
                        String uniqueWhere = column.getUnique_where();
                        if (!StringUtils.isNotEmpty(uniqueWhere)) {
                            uniqueWhere = column.getColumn_name() + "=#{" + column.getColumn_name() + "}";
                        }
                        List<Map<String,Object>> ls=sqlMapper.selectList("select "+tableName+"."+getTableKeyIdColumn(tableName)+","+column.getColumn_statement()+" "+funInfo.getFrom_sql()+" where "+uniqueWhere,mp);
                        if(ls==null||ls.isEmpty()) continue;//没有查到任何数据，符合
                       //存在1个以上结果，肯定不唯一，返回错误
                        return Result.failure("500","违反唯一性判断要求，请检查【"+column.getColumn_comment()+"】值！");
                    }
                }catch (Exception e){
                    throw e;
                }finally {
                    session.close();
                }
            }

            if (isNew) {//新增数据
                //获取newid
                String newid = mp.containsKey("newId")?(String)mp.get("newId"):getKeyUID();
                if (StringUtils.isEmpty(keyid)) {
                    mp.put(keycolumn, newid);
                }
                //根据功能配置，对页面无赋值字段设置默认值
                if(!tableName.equals("tool_function_column"))
                    mp=addDefaultValue2Map(mp);

                //自动编码
                if(AutoCodeColumns!=null&&AutoCodeColumns.size()>0){
                    for(ToolFunctionColumn codecolumn:AutoCodeColumns){
                        String code= (String) mp.get(codecolumn.getColumn_name());
                        if(StringUtils.isNotEmpty(code)) continue;
                        code=autoRuleCode(tableName,dbSource,codecolumn.getAuto_code(),codecolumn.getCompute_sql(),mp,codecolumn.getCode_length(),codecolumn.getPre_code());
                        mp.put(codecolumn.getColumn_name(),code);
                    }
                }
                //通过页面参数创建对象
                Class clazz = MemCache._entitys.get(tableName);
                entity = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
                entity = addDataInfo(entity);
                //打印
                E2MapUtil.pringEntity(entity, clazz);
                //插入记录
                getMapper(tableName).insertSelective(entity);
                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,"add");
                    if (!r.isSuccess()) return r;
                }
            } else {//修改数据
                //从数据库拿出对象
                //BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                //根据功能配置，对页面无赋值字段设置默认值
                if(!tableName.equals("tool_function_column"))
                    mp=addDefaultValue2Map(mp);

                //根据页面最新值更新对象//初始化对象
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, mp);
                entity = modifyDataInfo(entity);
                //打印
                Class clazz = MemCache._entitys.get(tableName);
                E2MapUtil.pringEntity(entity, clazz);
                //保存记录
                getMapper(tableName).updateByPrimaryKeySelective(entity);
                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,"update");
                    if (!r.isSuccess()) return r;
                }
            }

        }catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
       return Result.success();
    }
    /**
     *  gridsave 批量保存
     * @param funId
     * @param list
     * @throws Exception
     */

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?> gridSave(String funId,String tableName, List<Map> list ,Map<String, Object> requestMap) throws Exception {
        log.info("CommonService gridSave:" + funId  );
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        List<ToolFunctionColumn> SingleColumns =layoutService.getFunctionSingleColumn(funId);//唯一性判断
        List<ToolFunctionColumn> AutoCodeColumns =layoutService.getFunctionAutoColumn(funId);//自动编码判断
        String dbSource=DataSourceNames.DEFAULT;
        if(funInfo!=null&&StringUtils.isNotEmpty(funInfo.getTable_name())){
            if(tableName==null||tableName.equals("")) {
                tableName = funInfo.getTable_name().trim();
            }
            tableName=Map2EntityUtil.humpToLine2(tableName);// 避免传入的是实体名字，所以驼峰转换一次
            if(StringUtils.isNotEmpty(funInfo.getDb_source())){
                dbSource=funInfo.getDb_source().trim();
            }
        }
        //查询事件后触发内容
        String eventId=(String)requestMap.get("eventId");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        if(StringUtils.isEmpty(eventId)) eventId="zzzZZZ";
        Example example = new Example(ToolFunctionEventSql.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("event_id",eventId);
        criteria.andEqualTo("source_funid",funId);
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(example);

        log.info("set datasource is " + dbSource);
        DynamicDataSource.setDataSource(dbSource);//设置指定数据源
        try {
            String keycolumn = getTableKeyIdColumn(tableName);//主键列
//           System.out.println("keycolumn=========="+keycolumn);
            for (Map mp : list) {
                String keyid = (String) mp.get(keycolumn);
                boolean isNew=true;
                BaseEntity entity=null;
                if (StringUtils.isNotEmpty(keyid)) {
                    //从数据库拿出对象
                    entity = (BaseEntity) layoutService.getMapper(tableName).selectByPrimaryKey(keyid);
                    if(entity!=null){
                        isNew=false;
                    }
                }

                //判断唯一性
                if(SingleColumns!=null&&SingleColumns.size()>0){
                    SqlSession session = getSession(dbSource);
                    SqlMapper sqlMapper = new SqlMapper(session);
                    try {
                        for (ToolFunctionColumn column : SingleColumns) {
                            String v=(String) mp.get(column.getColumn_name());
                            if(StringUtils.isEmpty(v)) continue;
                            //传入值和现在对象值一样，不再判断
                            if(entity!=null){
                              String eval= (String) E2MapUtil.getEValueByKey(entity,getEntity(tableName),column.getColumn_name());
                              log.info("eval=========="+eval);
                               if(StringUtils.isNotEmpty(eval)&&eval.equals(v))
                                 continue;
                            }
                            String uniqueWhere = column.getUnique_where();
                            if (!StringUtils.isNotEmpty(uniqueWhere)) {
                                uniqueWhere = column.getColumn_name() + "=#{" + column.getColumn_name() + "}";
                            }
                            List<Map<String,Object>> ls=sqlMapper.selectList("select "+tableName+"."+getTableKeyIdColumn(tableName)+","+column.getColumn_statement()+" "+funInfo.getFrom_sql()+" where "+uniqueWhere,mp);
                            if(ls==null||ls.isEmpty()) continue;//没有查到任何数据，符合
                            //存在1个以上结果，肯定不唯一，返回错误
                            return Result.failure("500","违反唯一性判断要求，请检查【"+column.getColumn_comment()+"】值！");
                        }
                    }catch (Exception e){
                        throw e;
                    }finally {
                        session.close();
                    }
                }

                if (isNew) {//新增数据
                    //获取newid
                    String newid = getKeyUID();
                    if (StringUtils.isEmpty(keyid)) {
                        mp.put(keycolumn, newid);
                    }
                    //根据功能配置，对页面无赋值字段设置默认值
                    if(!tableName.equals("tool_function_column"))
                        mp=addDefaultValue2Map(mp);
                    //自动编码
                    if(AutoCodeColumns!=null&&AutoCodeColumns.size()>0){
                        for(ToolFunctionColumn codecolumn:AutoCodeColumns){
                            String code= (String) mp.get(codecolumn.getColumn_name());
                            if(StringUtils.isNotEmpty(code)) continue;
                            code=autoRuleCode(tableName,dbSource,codecolumn.getAuto_code(),codecolumn.getCompute_sql(),mp,codecolumn.getCode_length(),codecolumn.getPre_code());
                            mp.put(codecolumn.getColumn_name(),code);
                        }
                    }
                    //通过页面参数创建对象
                    Class clazz = MemCache._entitys.get(tableName);
                    entity = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
                    entity = addDataInfo(entity);
                    //打印
                    E2MapUtil.pringEntity(entity, clazz);
                    //插入记录
                    layoutService.getMapper(tableName).insertSelective(entity);
                    if(eventsqlList!=null&&eventsqlList.size()>0) {
                        Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,"add");
                        if (!r.isSuccess()) return r;
                    }
                } else {//修改数据
                    //从数据库拿出对象
                    //BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                    //根据功能配置，对页面无赋值字段设置默认值
                    if(!tableName.equals("tool_function_column"))
                        mp=addDefaultValue2Map(mp);
                    //根据页面最新值更新对象//初始化对象
                    entity = (BaseEntity) Map2EntityUtil.updateModel(entity, mp);
                    entity = modifyDataInfo(entity);
                    //打印
                    Class clazz = MemCache._entitys.get(tableName);
                    E2MapUtil.pringEntity(entity, clazz);
                    //保存记录
                    layoutService.getMapper(tableName).updateByPrimaryKeySelective(entity);
                    if(eventsqlList!=null&&eventsqlList.size()>0) {
                        Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,"update");
                        if (!r.isSuccess()) return r;
                    }
                }

            }
        }catch (Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
        return Result.success();
    }


//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
//    public void importdata(String importSQL,String sourcetableName,String targetbleName,String sourceColumns,String targetColumns,String[] keyids) throws Exception {
//        if(!StringUtils.isEmpty(importSQL)){//定义的是sql
//            for (String keyid : keyids) {
//                String newid = getKeyUID();
//                Map mp = new HashMap();
//                mp.put("newid", newid);//主键
//                mp.put("keyid", keyid);//主键
//                execute("default",importSQL,mp);
//            }
//        }else {//定义的是对应关系
//            String[] sourceColumnArray = sourceColumns.split(";");
//            String[] targetColumnArray = targetColumns.split(";");
//
//            for (String keyid : keyids) {
//                if(keyid.equals("")) continue;
//                String newid = getKeyUID();
//                log.info("CommonService importdata:" + sourcetableName + "sourceID:" + keyid + "  " + targetbleName + "  newID:" + newid);
//                //从数据库拿出对象
//                BaseEntity entity = (BaseEntity) getMapper(sourcetableName).selectByPrimaryKey(keyid);
//                Map sourceMp = E2MapUtil.E2Map(entity, getEntity(sourcetableName));
//                //创建目标表新对象
//                Map mp = new HashMap();
//                mp.put(getTableKeyIdColumn(targetbleName), newid);//主键
//                for (int i = 0; i < targetColumnArray.length; i++) {
//                    if (sourceMp.containsKey(sourceColumnArray[i])) {
//                        mp.put(targetColumnArray[i], sourceMp.get(sourceColumnArray[i]));//来自源的值
//                    } else {
//                        String defaultValue = sourceColumnArray[i];
//                        if (defaultValue.startsWith("'"))
//                            mp.put(targetColumnArray[i], defaultValue.replaceAll("'", ""));//默认字符
//                        else if (defaultValue.startsWith("{"))
//                            mp.put(targetColumnArray[i], getDefaultValue(defaultValue, "string"));//默认取值
//                    }
//                }
//                //根据map创建entity，并更新相关值
//                Class clazz = MemCache._entitys.get(targetbleName);
//                BaseEntity targetentity = (BaseEntity) Map2EntityUtil.createModel(clazz, mp);
//                targetentity = addDataInfo(targetentity);
//                //根据功能配置，对页面无赋值字段设置默认值
//
//                mp=addDefaultValue2Map(mp);
//                //打印
//                E2MapUtil.pringEntity(targetentity, clazz);
//                getMapper(targetbleName).insertSelective(targetentity);
//            }
//        }
//    }

    /**
     * 数据流事件，根据源funid和目标funid，事件类型来执行
     *
     * @param eventId
     * @param sourceFunId
     * @param targetFunId
     * @param keyids
     * @throws Exception
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?> dataflow(String eventId,String sourceFunId,String targetFunId, String importforeignKeyId,String[] keyids,String selectId)  {

        //获取功能设置信息
        ToolFunction sourceFunInfo=layoutService.getFunctionInfo(sourceFunId);
        ToolFunction targetFunInfo=layoutService.getFunctionInfo(targetFunId);
        log.info("CommonService dataflow begin....");
        Example example = new Example(ToolFunctionEventSql.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("event_id",eventId);
        criteria.andEqualTo("source_funid",sourceFunInfo.getFun_id());
        criteria.andEqualTo("target_funid",targetFunInfo.getFun_id());
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(example);
//
//        String sourceDbSource=DataSourceNames.DEFAULT;
//        String targetDbSource=DataSourceNames.DEFAULT;
//        if(sourceFunInfo!=null&&StringUtils.isNotEmpty(sourceFunInfo.getDb_source())){
//            sourceDbSource=sourceFunInfo.getDb_source().trim();
//        }
//        if(targetFunInfo!=null&&StringUtils.isNotEmpty(targetFunInfo.getDb_source())){
//            targetDbSource=targetFunInfo.getDb_source().trim();
//        }

        //循环执行
        for(String sourceKeyId:keyids){
            if(sourceKeyId.equals("")) continue;

            if(eventsqlList!=null&&eventsqlList.size()>0) {
                Result<?> r = eventRun(eventsqlList, sourceKeyId, importforeignKeyId,"import",selectId);
                if (!r.isSuccess()) return r;
            }
        }
        return Result.success();
    }

    /**
    **根据事件执行工作流
     **/

    public Result<?>  eventRun(List<ToolFunctionEventSql> eventsqlList,String sourceKeyId,String foreignKeyId,String eventType){
        return eventRun(eventsqlList,sourceKeyId,foreignKeyId,eventType,null);
    }
    public Result<?>  eventRun(List<ToolFunctionEventSql> eventsqlList,String sourceKeyId,String foreignKeyId,String eventType,String selectId){
        log.info("执行工作流begin");
        String newKeyId=getKeyUID();
        for(ToolFunctionEventSql eventSQL:eventsqlList){
            if(eventType!=null&&!eventSQL.getEvent_type().equals(eventType)) continue;
            //获取功能设置信息
            DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
            ToolFunction sourceFunInfo=layoutService.getFunctionInfo(eventSQL.getSource_funid());
            ToolFunction targetFunInfo=layoutService.getFunctionInfo(eventSQL.getTarget_funid());

            List<ToolFunctionColumn> AutoCodeColumns =layoutService.getFunctionAutoColumn(targetFunInfo.getFun_id());//目标功能自动编码判断
            DynamicDataSource.clearDataSource();
            String sourceDbSource=DataSourceNames.DEFAULT;
            String targetDbSource=DataSourceNames.DEFAULT;
            if(sourceFunInfo!=null&&StringUtils.isNotEmpty(sourceFunInfo.getDb_source())){
                sourceDbSource=sourceFunInfo.getDb_source().trim();
            }
            if(targetFunInfo!=null&&StringUtils.isNotEmpty(targetFunInfo.getDb_source())){
                targetDbSource=targetFunInfo.getDb_source().trim();
            }
            if(StringUtils.isNotEmpty(eventSQL.getSource_sql())){
                SqlSession sourceSession = getSession(sourceDbSource);
                SqlMapper sourceSqlMapper = new SqlMapper(sourceSession);
                SqlSession targetSession = getSession(targetDbSource);
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    //读取源数据
                    Map mp = new HashMap();
                    mp.put("selectId", selectId);
                    mp.put("sourceKeyId", sourceKeyId);
                    mp.put("importforeignKeyId", foreignKeyId);//在明细导入时是外键，主表导入时是选中的当前主键值
                    mp.put("foreignKeyId", foreignKeyId);//在明细导入时是外键，主表导入时是选中的当前主键值
                    mp = addDefaultValue2Map(mp);
                    log.info("Exec getSource_sql ==="+eventSQL.getSource_sql());
                    List<Map<String, Object>> dataList = sourceSqlMapper.selectList(SQLFormatUtil.formatSQL(getDBType(sourceDbSource),eventSQL.getSource_sql()), mp);
                    for(Map<String, Object> map:dataList) {
                        //写入目标表
                        if (map != null && !map.isEmpty() && StringUtils.isNotEmpty(eventSQL.getTarget_sql())) {
                            map.put("selectId", selectId);
                            map.put("sourceKeyId", sourceKeyId);
                            map.put("newKeyId", newKeyId);//主表主键
                            map.put("newDetKeyId", getKeyUID());//明细主键
                            map.put("importforeignKeyId", foreignKeyId);
                            map.put("foreignKeyId", foreignKeyId);
                            map = addDefaultValue2Map(map);
                            log.info("Exec getTarget_sql ===" + eventSQL.getTarget_sql());
                            if( eventSQL.getTarget_sql().indexOf("#{AUTOCODE}")>0){//含有{AUTOCODE}才创建自动编码
                                if(AutoCodeColumns!=null&&AutoCodeColumns.size()>0) {
                                    ToolFunctionColumn codecolumn = AutoCodeColumns.get(0);
                                    String code = (String) mp.get(codecolumn.getColumn_name());
                                    if (StringUtils.isNotEmpty(code)) continue;
                                    code = autoRuleCode(targetFunInfo.getTable_name(), targetFunInfo.getDb_source(), codecolumn.getAuto_code(), codecolumn.getCompute_sql(), mp, codecolumn.getCode_length(), codecolumn.getPre_code());

                                    map.put("AUTOCODE", code);
                                }
                            }
                            targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(targetDbSource), eventSQL.getTarget_sql()), map);
                        }
                    }
                }catch (Exception e){
                    log.error("数据流事件执行出错",e);
                    return Result.failure("500","数据流事件执行出错");
                    // throw e;
                }finally {
                    sourceSession.close();
                    targetSession.close();
                }
            }

            if(StringUtils.isNotEmpty(eventSQL.getBo_function())) {

                Map mp = new HashMap();
                mp.put("selectId", selectId);
                mp.put("newKeyId", newKeyId);
                mp.put("sourceKeyId", sourceKeyId);
                mp.put("importforeignKeyId", foreignKeyId);
                mp.put("foreignKeyId", foreignKeyId);
                mp = addDefaultValue2Map(mp);

                if(eventSQL.getBo_function().startsWith("com.")){//class
                    boolean r=(boolean) ExecClassMethodUtil.ExecMethod(eventSQL.getBo_function(),true,mp) ;
                    if(!r){
                        log.error("数据流事件执行出错"+eventSQL.getBo_function());
                        return Result.failure("500", "数据流事件执行出错"+eventSQL.getBo_function());
                    }
                }else {
                    SqlSession targetSession = getSession(targetDbSource);
                    SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                    try {
                        log.info("Exec getTarget_sql ===" + eventSQL.getTarget_sql());
                        targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(targetDbSource), eventSQL.getTarget_sql()), mp);
                    } catch (Exception e) {
                        log.error("数据流事件执行出错", e);
                        return Result.failure("500", "数据流事件执行出错");
                        // throw e;
                    } finally {
                        targetSession.close();
                    }
                }
            }
        }
        return Result.success();
    }

    /**
     * 根据页面传值更新选中记录的指定字段值
     * @param requestMap
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?>  update(String[] keyids,Map<String, Object> requestMap) throws Exception {
        String funId=(String)requestMap.get("funId");
        String eventId=(String)requestMap.get("eventId");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        String tableName="";
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        //查询事件后触发内容
        Example example = new Example(ToolFunctionEventSql.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("event_id",eventId);
        criteria.andEqualTo("source_funid",funInfo.getFun_id());
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(example);


        String dbSource=DataSourceNames.DEFAULT;
        try {
            for(String keyid:keyids) {
                if(keyid.equals("")) continue;
                if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTable_name())) {
                    tableName = funInfo.getTable_name().trim();
                    if (StringUtils.isNotEmpty(funInfo.getDb_source())) {
                        dbSource = funInfo.getDb_source().trim();
                    }
                    DynamicDataSource.setDataSource(dbSource);//设置指定数据源
                    log.info("set datasource is " + dbSource);
                }

                BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                //根据页面最新值更新对象//初始化对象
                requestMap=addDefaultValue2Map(requestMap);
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, requestMap);
                entity = modifyDataInfo(entity);
                //保存记录
                getMapper(tableName).updateByPrimaryKeySelective(entity);
                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, keyid, foreignKeyId,null);
                    if (!r.isSuccess()) return r;
                }
            }
        }catch(Exception e){
            log.error("事件执行出错",e);
            return Result.failure("500","事件执行出错");
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
        return Result.success();
    }

    //    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?>  exec( Map<String, Object> requestMap) throws Exception {
        String funId=(String)requestMap.get("funId");
        String eventId=(String)requestMap.get("eventId");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        //查询事件后触发内容
        Example example = new Example(ToolFunctionEventSql.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("event_id",eventId);
        criteria.andEqualTo("source_funid",funInfo.getFun_id());
        List<ToolFunctionEventSql> eventsqlList=toolFunctionEventSqlMapper.selectByExample(example);

        try {

                if(eventsqlList!=null&&eventsqlList.size()>0) {
                    Result<?> r = eventRun(eventsqlList, null, foreignKeyId,null);
                    if (!r.isSuccess()) return r;
                }
        }catch(Exception e){
            log.error("事件执行出错",e);
            return Result.failure("500","事件执行出错");
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
        return Result.success();
    }



    /**
     * batchUpdate  批量更新
     * @param funId
     * @param keyids
     */
//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public void batchUpdate(String funId,String[] keyids,Map<String, Object> requestMap) throws Exception {
        log.info("CommonService delete:" + funId);
        String tableName = "tool_table_column";
        //获取功能设置信息
        ToolFunction funInfo = layoutService.getFunctionInfo(funId);
        String dbSource = DataSourceNames.DEFAULT;
        if (funInfo != null && StringUtils.isNotEmpty(funInfo.getTable_name())) {
            tableName = funInfo.getTable_name().trim();
            if (StringUtils.isNotEmpty(funInfo.getDb_source())) {
                dbSource = funInfo.getDb_source().trim();
            }
            DynamicDataSource.setDataSource(dbSource);//设置指定数据源
            log.info("set datasource is " + dbSource);
        }

        try {
            for(String keyid:keyids) {
                if(keyid.equals("")) continue;
                BaseEntity entity = (BaseEntity) getMapper(tableName).selectByPrimaryKey(keyid);
                //根据页面最新值更新对象//初始化对象
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, requestMap);
                entity = modifyDataInfo(entity);
                //保存记录
                getMapper(tableName).updateByPrimaryKeySelective(entity);
            }
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSource.clearDataSource();//清理
            log.info("clean datasource");
        }
    }

    /**
     *
     * 获取自动编码
     * @param funId
     * @param colName
     * @param requestMap
     * @return
     */
    public String getRuleCode(String funId,String colName,Map<String, Object> requestMap){
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        ToolFunctionColumn column=layoutService.getFunctionColumnOne(funId,colName);
        if(column==null) return getUUID();
        return autoRuleCode(funInfo.getTable_name(),funInfo.getDb_source(),column.getAuto_code(),column.getCompute_sql(),requestMap,column.getCode_length(),column.getPre_code());

    }

}
