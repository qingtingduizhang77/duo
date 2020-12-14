package com.duo.core.utils;

import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.SpringContextHolder;
import com.duo.core.vo.SQLParseBean;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [ Yonsin ] on [2019/8/10]
 * @Description： [ 跨数据支持统一SQL语法 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class SQLFormatUtil {
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormat");

    protected  static ConcurrentHashMap<String, String> sqlMap=new ConcurrentHashMap();

    /**
     * SQL根据数据库自适应
     * @param dbType
     * @param sqlStatement
     * @return
     */
    public static String formatSQL(String dbType,String sqlStatement){
        String md5=getMD5(dbType+"::"+sqlStatement);
        if(sqlMap.containsKey(md5)) return sqlMap.get(md5);
        //非sqlserver数据库不需要dbo.
        Map<String,SQLParseBean> mp=sqlprop.getSqlformat();

        for (Map.Entry<String, SQLParseBean> entry : mp.entrySet()) {
            String reg=entry.getKey();
            if(sqlStatement.indexOf("{"+reg+"}")<0){//不含该函数则continue
                continue;
            }
            SQLParseBean bean=entry.getValue();//获取设置参数

            String tarReg=bean.getMySQL();
            if(dbType.equals("Oracle")){
                tarReg=bean.getOracle();
            }else if(dbType.equals("SQLServer")){
                tarReg=bean.getSQLServer();
            }
            int paramNum=Integer.parseInt(bean.getParamNum());
            if(paramNum==0){//无参数直接替换即可
                sqlStatement=sqlStatement.replaceAll("\\{"+reg+"\\}",tarReg);
                continue;
            }
            sqlStatement=formatSQL(sqlStatement,"{"+reg+"}",tarReg,paramNum);
        }
        sqlMap.put(md5,sqlStatement);
        return sqlStatement;
    }


    /**
     * 根据函数格式替换参数
     *     仅支持最多2个参数，并且不支持太复杂的SQL
     * @param sql
     * @param reg
     * @param tarReg
     * @param paramNum
     * @return
     */
    private static String  formatSQL(String sql,  String reg, String tarReg, int paramNum){
        //判断是否存在需要替换的字符
        String sql1=sql.substring(0,sql.indexOf(reg));
        String sql2=sql.substring(sql.indexOf(reg)+reg.length());
        String paramsql="";
        if(sql2.indexOf(")")>0) paramsql=sql2.substring(sql2.indexOf("(")+1, sql2.indexOf(")"));
        else paramsql=sql2.substring(sql2.indexOf("(")+1);
        String othersql="";
        if(sql2.indexOf(")")>0)  othersql=sql2.substring(sql2.indexOf(")")+1);

        if(paramsql.indexOf(reg)>-1){
            paramsql=formatSQL(paramsql,reg,tarReg,paramNum);
        }
        //根据参数数量处理
        if(paramNum==1)   paramsql=tarReg.replace("@v1",paramsql);
        else  if(paramNum==2){
            paramsql=tarReg.replace("@v1",paramsql.substring(0,paramsql.indexOf(",")))
                    .replace("@v2",paramsql.substring(paramsql.indexOf(",")+1));
        }

        if(othersql.indexOf(reg)>-1) othersql=formatSQL(othersql,reg,tarReg,paramNum);
        return sql1  + paramsql +  othersql;
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5加密出现错误");
        }
        return str;
    }
}
