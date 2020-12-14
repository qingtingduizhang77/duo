package com.duo.core;

import com.baidu.fsg.uid.UidGenerator;
import com.duo.Constants;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.utils.*;
import com.duo.modules.system.entity.SystemLog;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.system.mapper.SystemLogDetailMapper;
import com.duo.modules.system.mapper.SystemLogMapper;
import com.duo.modules.system.mapper.SystemMajorfuncMapper;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [ Yonsin ] on [2019/8/9]
 * @Description： [ 公共方法 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class BaseService {
    @Autowired
    protected  UidGenerator uidGenerator;
    @Resource
    public LogCenter logdb;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private I18nMessage localeMessage;
    @Autowired
    private SystemMajorfuncMapper systemMajorfuncMapper;


    /**
     * 根据表名获取主键id
     * @param tableName
     * @return
     */
    public  String getTableKeyIdColumn(String tableName){
        String keycolumn=MemCache._tableInfo.get(tableName+".id");
        if(keycolumn==null) keycolumn=MemCache._tableInfo.get(MemCache._tableInfo.get(tableName)+".id");//可能存入的是Entity名
        return keycolumn;
    }


    /**
     * 根据表名获取字段列表
     * @param tableName
     * @return   xxx,yyyy,tttt,fff   格式
     */
    public  String getTableColumns(String tableName){
        String  columns=MemCache._tableInfo.get(tableName+".column");
        if(columns==null) columns=MemCache._tableInfo.get(MemCache._tableInfo.get(tableName)+".column");//可能存入的是Entity名
        return columns;
    }



    /**
     * 如果多个Mapper同名，需要写类的详细路径
     *          table_name和eitity实体名字都可以查
     * @param mapperName
     * @return
     */
    public static MyMapper getMapper(String mapperName){
        try {
            if(mapperName.indexOf("_")>0) mapperName=MemCache._tableInfo.get(mapperName);//传入的可能是表名名
            Class clazz= MemCache._mappers.get(mapperName);
            if(clazz==null) clazz=Class.forName(mapperName);//传入的可能是全路径
            MyMapper mapper=  (MyMapper) SpringContextHolder.getBean(clazz);

            return mapper;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 如果多个Entity同名，需要写类的详细路径
     *          table_name和eitity实体名字都可以查
     * @param entityName
     * @return
     */
    public Class getEntity(String entityName){
        try {
            Class clazz= MemCache._entitys.get(entityName);
            if(clazz==null) clazz=Class.forName(entityName);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 指定数据源获取SqlMapper
     */
    public  SqlSession getSession(String dataSourceName){
        try {
            if(dataSourceName==null||dataSourceName.equals("")){//从缓存里拿SQLMapper
                dataSourceName= DataSourceNames.DEFAULT;
            }
            log.info("SqlSession dataSource::::"+dataSourceName);
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(DynamicDataSource.dsMap.get(dataSourceName));

            //手工配置
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setCallSettersOnNulls(true);
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setCacheEnabled(true);
            configuration.setLazyLoadingEnabled(true);
            configuration.setJdbcTypeForNull(JdbcType.NULL);
            configuration.setAggressiveLazyLoading(true);
            sqlSessionFactoryBean.setConfiguration(configuration);

            SqlSession session=   session =sqlSessionFactoryBean.getObject().openSession();// DynamicDataSource.dsSqlSessionMap.get(dataSourceName).getObject().openSession();

            return session;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  指定数据源获取数据库类型
     */
    protected ConcurrentHashMap<String ,String> _dbtypes=new ConcurrentHashMap<String ,String>();//缓存数据源的数据库类型
    public  String getDBType(String dataSourceName){
        if(_dbtypes.containsKey(dataSourceName)) return _dbtypes.get(dataSourceName);

        try {
            if(dataSourceName==null||dataSourceName.equals("")){
                dataSourceName= DataSourceNames.DEFAULT;
            }
            DataSource ds=DynamicDataSource.dsMap.get(dataSourceName);
            String dbtype=ds.getConnection().getMetaData().getDatabaseProductName();
            ds.getConnection().close();
            log.info("JdbcType::::"+dbtype);
            _dbtypes.put(dataSourceName,dbtype);
            return dbtype;
        } catch (Exception e) {
            e.printStackTrace();
        }
        _dbtypes.put(dataSourceName,"MySQL");
        return "MySQL";
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }


    /**
     * 获得一个百度UUID
     * @return String UUID
     */
    public   String getKeyUID(){
        return Long.toString(uidGenerator.getUID());
    }

    /**
     * 新增数据 加上add_userid  add_date  record_flag值
     * @param entity
     * @return
     */
    public BaseEntity addDataInfo(BaseEntity entity){
        SystemUser curuser=(SystemUser)ShiroUtils.getUser();
        entity.setAdd_date(new Date());
        entity.setAdd_userid(curuser.getUser_id() );
        if(entity.getRecord_flag()==null) entity.setRecord_flag("0");//0 正常，9 标记删除
        if(entity.getProject_id()==null) entity.setProject_id(ShiroUtils.getCompanyId());
        return entity;
    }

    /**
     * 更新数据 加上modify_userid  modify_date
     * @param entity
     * @return
     */
    public BaseEntity modifyDataInfo(BaseEntity entity){
        SystemUser curuser=(SystemUser)ShiroUtils.getUser();
        entity.setModify_date(new Date());
        entity.setModify_userid(curuser.getUser_id() );
        return entity;
    }

    /**
     * 树形结构数据id生成
     * @param dbsourceName
     * @param tableName
     * @param prentId
     * @return
     */
    public ConcurrentHashMap<String ,Integer> _treeIDMap=new ConcurrentHashMap<String ,Integer>();//存储所有检索到的selectSQL
    public String getTreeID(String dbsourceName,String tableName,Object prentId){

        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        String keycolumn=MemCache._tableInfo.get(tableName+".id");
        try {
            Map<String, Object> mp = sqlMapper.selectOne("select max(" + keycolumn + ") as maxid from " + tableName + " where " + keycolumn + " like '" + prentId + "____'");
            // System.out.println("select max("+keycolumn+") as maxid from "+tableName+" where "+keycolumn+" like '"+prentId+"____'");
            if (null != mp) {
                Object maxid = mp.get("maxid");
                if (maxid != null) {
                    int max = Integer.parseInt(String.valueOf(maxid).substring(String.valueOf(prentId).length()));
                    max++;
                    if (_treeIDMap.containsKey(tableName + prentId)) {
                        int last = _treeIDMap.get(tableName + prentId);
                        if (max <= last) max = last + 1;
                    }
                    _treeIDMap.put(tableName + prentId, max);
                    System.out.println("maxid=" + maxid + "    max=" + max);
                    return String.valueOf(prentId) + String.valueOf(max);
                }
            }
        }catch(Exception e){

        }finally {
            session.close();
        }
        int max=1001;
        if(_treeIDMap.containsKey(tableName+prentId)){
            int last=_treeIDMap.get(tableName+prentId);
            if(max<=last) max=last+1;
        }
        _treeIDMap.put(tableName+prentId,max);
        return String.valueOf(prentId)+max;
    }


    /**
     * 根据map数据进行计算
     * @return
     *     true  唯一
     *     false 不唯一
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public String compute(String dbSourceName,String computeSQL,Map<String, Object> parameterMap) {
       if(computeSQL.indexOf("{DUAL}")<0) computeSQL="select "+computeSQL+" as cnt {DUAL}";//利用虚拟表计算
        log.info("BaseService compute:" + computeSQL);

        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            Map<String, Object> cntMp = sqlMapper.selectOne(SQLPageHelper.CountSqlPage(getDBType(dbSourceName), computeSQL), parameterMap);
            return String.valueOf(cntMp.get("cnt")) ;
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 根据id取text
     *    ——多选项用,号分隔存储
     *    ——sql中必须有 as text
     * @return
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public String idToText(String dbSourceName,String textSQL,String values) {
        if(values==null||values.trim().equals("")) return values;//空数据直接返回

        values="'"+values.replaceAll(",","','")+"'";
        log.info("BaseService idToText:" + textSQL+"    values"+values);

        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            List<Map<String, Object>> textLs = sqlMapper.selectList(SQLFormatUtil.formatSQL(dbSourceName, textSQL), values);
            StringBuffer returnText=new StringBuffer("");
            for(Map<String,Object> mp:textLs){
                returnText.append(mp.get("text")+",");
            }

            return returnText.length()==0?values:returnText.substring(0,returnText.length()-1);
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 判断唯一
     * @return
     *     true  唯一
     *     false 不唯一
     */
//    @Transactional(propagation= Propagation.NOT_SUPPORTED)//不需要事务管理
    public boolean unique(String dbSourceName,String uniqueSQL,Map<String, Object> parameterMap) {
        log.info("BaseService unique:" + uniqueSQL);

        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            Map<String, Object> cntMp = sqlMapper.selectOne(SQLPageHelper.CountSqlPage(getDBType(dbSourceName), uniqueSQL), parameterMap);
            if (Integer.parseInt(String.valueOf(cntMp.get("cnt"))) > 0) return false;
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
        return true;
    }

    /**
     * 执行SQL
     * @return
     */
//    @Transactional(rollbackFor=Exception.class)
    public void execute(String dbSourceName,String sql,Map<String, Object> parameterMap) {
        log.info( "BaseService execute:"+sql);

        SqlSession session =getSession(dbSourceName);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            sqlMapper.update(SQLFormatUtil.formatSQL(getDBType(dbSourceName),sql),parameterMap);
            session.commit();
        }catch(Exception e){
            throw e;
        }finally {
            session.close();
        }
    }

    /**
     * 执行事件前后触发
     *       preEvevt  事件前
     *       postEvent 事件后
     * @return
     */
//    @Transactional(rollbackFor=Exception.class)
    public void execEvent(String dbSourceName,String eventType,List<Map<String,String>> sqls,Map<String, Object> parameterMap){
        for(Map<String,String> sqlmp:sqls) {
            if(sqlmp.get("event_type").equals(eventType)&&sqlmp.get("is_valid").equals("1")) {//事件是否生效，事件类型
                String sql=sqlmp.get("sql_statement");
                log.info("BaseService execEvent "+eventType+":" + sql);
                execute(dbSourceName,sql, parameterMap);
            }
        }
    }

    /**
     *  往Map添加默认值
     * @param mp
     * @return
     */
    public Map<String,Object> addDefaultValue2Map(Map<String,Object> mp){
        if(mp.containsKey("CURRENTYEAR")) return mp;//防止多次赋值浪费性能
        //1.将值里的默认值替换
        for (Map.Entry<String, Object> entry : mp.entrySet()) {
            if(entry.getValue()!=null
                    &&entry.getValue().getClass().getCanonicalName().equals("java.lang.String")
                    &&entry.getValue().toString().startsWith("{")){
                mp.put(entry.getKey(),getDefaultValue(entry.getValue().toString(),"string"));
            }
        }
        //2.将默认值作为参数给sql使用
        mp.put("CURRENTYEAR",DateUtils.getYear());
        mp.put("CURRENTMONTH",DateUtils.getMonth());
        mp.put("CURRENTDATE",DateUtils.getDate());
        mp.put("CURRENTTIME",DateUtils.getDateTime());
        if(!ShiroUtils.isLogin()) return mp;
        mp.put("CURRENTUSERNAME",ShiroUtils.getUserName());
        mp.put("CURRENTUSERID",ShiroUtils.getUserId());
        mp.put("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        mp.put("CURRENTDEPTID",ShiroUtils.getDeptId());
        mp.put("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        mp.put("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        mp.put("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        mp.put("CURRENTPROJECTID",ShiroUtils.getProjectId());
        mp.put("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        return mp;
    }

    /**\
     * 根据既定参数返回默认值
     *    dataType: date/string，缺省默认string
     * @param orgValue
     * @param dataType
     * @return
     */
    public static Object getDefaultValue(String orgValue,String dataType){
        if(dataType.equals("date")){
            return new Date();
        }
        String newValue=orgValue.trim();
        if(newValue.equals("{CURRENTYEAR}")){
            return DateUtils.getYear();
        }else if(newValue.equals("{CURRENTMONTH}")){
            return DateUtils.getMonth();
        }else if(newValue.equals("{CURRENTDATE}")){
            return DateUtils.getDate();
        }else  if(newValue.equals("{CURRENTTIME}")){
            return DateUtils.getDateTime();
        }

        if(!ShiroUtils.isLogin()) return orgValue;
        if(newValue.equals("{CURRENTUSERNAME}")){
            return ShiroUtils.getUserName();
        }else if(newValue.equals("{CURRENTUSERID}")){
            return ShiroUtils.getUserId();
        }else if(newValue.equals("{CURRENTDEPTNAME}")){
            return ShiroUtils.getDeptName();
        }else if(newValue.equals("{CURRENTDEPTID}")){
            return ShiroUtils.getDeptId();
        }else if(newValue.equals("{CURRENTCOMPANYID}")){
            return ShiroUtils.getCompanyId();
        }else if(newValue.equals("{CURRENTONEDEPTID}")){
            return ShiroUtils.getOneDeptId();
        }else if(newValue.equals("{CURRENTTWODEPTID}")){
            return ShiroUtils.getTwoDeptId();
        } else if(newValue.equals("{CURRENTPROJECTID}")){
             return ShiroUtils.getProjectId();
        }else if(newValue.equals("{CURRENTSYSTEMID}")){
            return ShiroUtils.getSystemId();
        }
        //无匹配返回原值
        return orgValue;
    }

    public static String replaceDefaultValue(String orgSQL){

        if(orgSQL.indexOf("{CURRENTYEAR}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTYEAR\\}", DateUtils.getYear());
        }
        if(orgSQL.indexOf("{CURRENTMONTH}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTMONTH\\}",DateUtils.getMonth());
        }
        if(orgSQL.indexOf("{CURRENTDATE}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTDATE\\}",DateUtils.getDate());
        }
        if(orgSQL.indexOf("{CURRENTTIME}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTTIME\\}", DateUtils.getDateTime());
        }

        if(!ShiroUtils.isLogin()) return orgSQL;
        if(orgSQL.indexOf("{CURRENTUSERNAME}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTUSERNAME\\}", ShiroUtils.getUserName());
        }
        if(orgSQL.indexOf("{CURRENTUSERID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTUSERID\\}", ShiroUtils.getUserId());
        }
        if(orgSQL.indexOf("{CURRENTDEPTNAME}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTDEPTNAME\\}", ShiroUtils.getDeptName());
        }
        if(orgSQL.indexOf("{CURRENTDEPTID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTDEPTID\\}", ShiroUtils.getDeptId());
        }
        if(orgSQL.indexOf("{CURRENTCOMPANYID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTCOMPANYID\\}", ShiroUtils.getCompanyId());
        }
        if(orgSQL.indexOf("{CURRENTONEDEPTID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTONEDEPTID\\}", ShiroUtils.getOneDeptId());
        }
        if(orgSQL.indexOf("{CURRENTTWODEPTID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTTWODEPTID\\}", ShiroUtils.getTwoDeptId());
        }
        if(orgSQL.indexOf("{CURRENTPROJECTID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTPROJECTID\\}", ShiroUtils.getProjectId());
        }
        if(orgSQL.indexOf("{CURRENTSYSTEMID}")>-1){
            orgSQL=orgSQL.replaceAll("\\{CURRENTSYSTEMID\\}", ShiroUtils.getSystemId());
        }
        //无匹配返回原值
        return orgSQL;
    }


    /**
     *  /**
     *  规则自动编码，可指定长度自动填充字符
     *    ——sequence：流水号顺序，system_tablecode记录表序号
     *    ——monsequence：流水号顺序
     *    ——sql:基于sql运算生成
     *    ——uuid：MD5随机生成
     *    ——uid：百度uid随机生成
     *    ——class：执行自定义class计算得到
     * @param dbSourceName
     * @param codeType
     * @param codeSQL
     * @param parameterMap
     * @param length
     * @return
     */
    public String autoRuleCode(String tableName,String dbSourceName,String codeType,String codeSQL,Map<String, Object> parameterMap,int length,String preCode){
        if(codeType==null||codeType.equals("")||"yearseq;monseq;sequence;sql;uuid;uid;class".indexOf(codeType)<0) codeType="sequence";
        if(preCode==null) preCode="";
        log.info( "BaseService autoRuleCode:"+codeType);
        if(codeType.equals("uuid")) return preCode+getUUID();
        if(codeType.equals("uid")) return preCode+getKeyUID();
        String fillchar="0";
        if(codeType.equals("sequence")){
            //利用Redis产生序列
            Double newcode=RedisUtil.hincr("SystemCodes",tableName,1);
            String code=String.valueOf(Math.round(newcode));
            for (int i = code.length(); i < length; i++) {
                code = fillchar + code;
            }

            return preCode+code;
        }

        if(codeType.equals("monseq")){
            //利用Redis产生序列
            if(!RedisUtil.hasKey("SystemCodes"+tableName+DateUtils.getYear()+DateUtils.getMonth()))
                RedisUtil.set("SystemCodes"+tableName+DateUtils.getYear()+DateUtils.getMonth(),0,3600*24*31);//一个月失效
            long newcode=RedisUtil.incr("SystemCodes"+tableName+DateUtils.getYear()+DateUtils.getMonth(),1);
            String code=String.valueOf(newcode);
            for (int i = code.length(); i < length; i++) {
                code = fillchar + code;
            }
            code=DateUtils.getYear()+DateUtils.getMonth()+code;//加上月份前缀
            return preCode+code;
        }


        if(codeType.equals("yearseq")){
            //利用Redis产生序列
            if(!RedisUtil.hasKey("SystemCodes"+tableName+DateUtils.getYear()))
                RedisUtil.set("SystemCodes"+tableName+DateUtils.getYear(),0,3600*24*366);//一年失效
            long newcode=RedisUtil.incr("SystemCodes"+tableName+DateUtils.getYear(),1);
            String code=String.valueOf(newcode);
            for (int i = code.length(); i < length; i++) {
                code = fillchar + code;
            }
            code=DateUtils.getYear()+code;//加上年份前缀
            return preCode+code;
        }
        if(codeType.equals("sql")){

            SqlSession session =getSession(dbSourceName);
            SqlMapper sqlMapper=new SqlMapper(session);
            try {
                Map<String, Object> cntMp = sqlMapper.selectOne(codeSQL, parameterMap);

                String code = cntMp==null||cntMp.isEmpty()||cntMp.get("codemax")==null?"1":String.valueOf(cntMp.get("codemax"));
              if(cntMp==null||cntMp.isEmpty()||cntMp.get("precode")==null){

              }else{
                  preCode=String.valueOf(cntMp.get("precode"));
              }
                if(StringUtils.isEmpty(code)) code="1";
                for (int i = code.length(); i < length; i++) {
                    code = fillchar + code;
                }
                return preCode+code;
            }catch (Exception e){
                throw e;
            }finally {
                session.close();
            }
        }

        if(codeType.equals("class")){
            String code=String.valueOf(ExecClassMethodUtil.ExecMethod(codeSQL,"1",parameterMap));
            for(int i=code.length();i<length;i++){
                code=fillchar+code;
            }
            return preCode+code;
        }
        return  getKeyUID();
    }
}
