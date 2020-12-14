package com.duo.core.utils;

import com.duo.core.BaseService;
import com.duo.core.LogCenterStatic;
import lombok.extern.slf4j.Slf4j;

/**
 * @author [ Yonsin ] on [2019/8/10]
 * @Description： [ 支持多数据库的SQL分页 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class SQLPageHelper {
   private static LogCenterStatic  log=new LogCenterStatic();
    /**
     * 分页入口类
     * @param dbtype
     * @param SelectSQL
     * @param OrderBySQL
     * @return
     */
    public static String SqlPage(String dbtype,String SelectSQL,String OrderBySQL){
        //默认直接合并
        String pageSQL=SelectSQL+(OrderBySQL==null ? "":" "+OrderBySQL);

        //根据数据库类型匹配分页SQL
        if(dbtype.equals("MySQL")) pageSQL= MySQLPage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("SQLServer")) pageSQL= SQLServerPage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("SQLServer2005")) pageSQL= SQLServer2005Page(SelectSQL,OrderBySQL);
        else if(dbtype.equals("Oracle")) pageSQL= OraclePage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("PostgreSQL")) pageSQL= PostgreSQLPage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("DB2")) pageSQL= DB2Page(SelectSQL,OrderBySQL);
        else if(dbtype.equals("MariaDB")) pageSQL= MariaDBPage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("MongoDB")) pageSQL= MongoDBPage(SelectSQL,OrderBySQL);
        else if(dbtype.equals("DM")) pageSQL= DMPage(SelectSQL,OrderBySQL);

        //语法兼容处理
        return BaseService.replaceDefaultValue(SQLFormatUtil.formatSQL(dbtype,pageSQL));
    }

    public static String CountSqlPage(String dbtype,String SelectSQL){
        String pageSQL="select count(*) as cnt from ("+SelectSQL+") z";
        System.out.println("CountSqlPage:::"+pageSQL);
        //语法兼容处理
        return BaseService.replaceDefaultValue(SQLFormatUtil.formatSQL(dbtype,pageSQL));
    }

    /**
     * SQLServer 2000数据库
     */
    private static String SQLServerPage(String SelectSQL,String OrderBySQL){

        return SQLServer2005Page(SelectSQL,OrderBySQL);
    }


    /**
     * SQLServer 2005数据库
     */
    private static String SQLServer2005Page(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="ORDER BY (SELECT 0)";
        String pageSQL="SELECT * FROM (SELECT ROW_NUMBER() OVER ("+OrderBySQL+") AS ROWNUM,* FROM ("+SelectSQL+")) t WHERE t.ROWNUM > #{offset} AND t.ROWNUM <= #{limit}";
        log.info("SQLServer PageSQL==="+pageSQL);
        return pageSQL;
    }


    /**
     * Oracle数据库
     */
    private static String OraclePage(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="";
        String pageSQL="SELECT * FROM (SELECT ROWNUM RN,t.* FROM ("+SelectSQL+" "+OrderBySQL+") t WHERE ROWNUM <= #{offset}+#{limit}) WHERE RN > #{offset}";
        log.info("Oracle PageSQL==="+pageSQL);
        return pageSQL;
    }


    /**
     * MySQL数据库
     */
    private static String MySQLPage(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="";
        String pageSQL=SelectSQL+" "+OrderBySQL+" LIMIT #{limit} OFFSET #{offset}";
        log.info("MySQL PageSQL==="+pageSQL);
        return pageSQL;
    }


    /**
     * MariaDB 数据库
     */
    private static String MariaDBPage(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="";
        String pageSQL=SelectSQL+" "+OrderBySQL+" LIMIT #{limit} OFFSET #{offset}";
        log.info("MariaDB PageSQL==="+pageSQL);
        return pageSQL;
    }




    /**
     * PostgreSQL数据库
     */
    private static String PostgreSQLPage(String SelectSQL,String OrderBySQL){

        return "";
    }


    /**
     * DB2数据库
     */
    private static String DB2Page(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="";
        String pageSQL="SELECT * FROM (SELECT ROW_NUMBER() OVER ("+OrderBySQL+") AS ROWNUM,tt.* FROM ("+SelectSQL+") tt) t WHERE t.ROWNUM > #{offset} AND t.ROWNUM <= #{limit}";
        log.info("DB2 PageSQL==="+pageSQL);
        return pageSQL;
    }



    /**
     * 达梦数据库
     */
    private static String DMPage(String SelectSQL,String OrderBySQL){
        if(OrderBySQL==null) OrderBySQL="";
        String pageSQL="SELECT * FROM (SELECT ROWNUM RN,t.* FROM ("+SelectSQL+" "+OrderBySQL+") t WHERE ROWNUM <= #{limit}) WHERE RN > #{offset}";
        log.info("DM PageSQL==="+pageSQL);
        return pageSQL;
    }

    /**
     * MongoDB 数据库
     */
    private static String MongoDBPage(String SelectSQL,String OrderBySQL){

        return "";
    }
}
