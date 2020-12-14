package com.duo.core;

import com.baidu.fsg.uid.UidGenerator;
import com.duo.MemCache;
import com.duo.core.utils.ShiroUtils;
import com.duo.modules.system.entity.SystemLog;
import com.duo.modules.system.entity.SystemLogDetail;
import com.duo.modules.system.mapper.SystemLogDetailMapper;
import com.duo.modules.system.mapper.SystemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.reflect.Reflection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author [ Yonsin ] on [2019/10/16]
 * @Description： [ 日志处理中心 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Component
@Scope("prototype") //设置多例模式
public class LogCenter {
    @Autowired
    protected UidGenerator uidGenerator;
    @Autowired
    private SystemLogMapper systemLogMapper;
    @Autowired
    private SystemLogDetailMapper systemLogDetailMapper;
    private  SystemLog logentity;
    private  SystemLog logentitySession;
    private String _logID=UUID.randomUUID().toString().replaceAll("-", "");
    private String _sessionID;

    private static Map<String,Integer> levelMap=new HashMap<String,Integer>();
    static{
        levelMap.put("debug",1);
        levelMap.put("info",2);
        levelMap.put("warn",3);
        levelMap.put("error",4);
    }

    public void info(String message) {
        setLog("info", message,"","");
    }

    public void warn(String message) {
        setLog("warn", message,"","");
    }

    public void error(String message) {
        setLog("error",message,"","");
    }

    public void debug(String message) {
        setLog("debug", message,"","");
    }
 

    public void error(String message, Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        setLog("error", message + stringWriter.toString(), "", "");
    }

    public void info(String message, String funId, String dataId) {
        setLog("info", message, funId, dataId);
    }

    public void warn(String message, String funId, String dataId) {
        setLog("warn", message, funId, dataId);
    }

    public void error(String message, String funId, String dataId) {
        setLog("error", message, funId, dataId);
    }

    public void debug(String message, String funId, String dataId) {
        setLog("debug", message, funId, dataId);
    }


    public void error(String message, Exception e, String funId, String dataId) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        setLog("error", message + stringWriter.toString(),  funId, dataId);
    }

    public void setLog( String level, String message, String funId, String dataId) {
        if(ShiroUtils.getSessionId()==null)
            setLog( _logID,level,message,funId,dataId);
        else
            setLogSession( ShiroUtils.getSessionId().replaceAll("-", ""),level,message,funId,dataId);
    }

    /**
     * 公共日志统一logid
     * @param log_id
     * @param level
     * @param message
     * @param funId
     * @param dataId
     */
    public void setLog(String log_id, String level, String message, String funId, String dataId) {
        try {
            message = Reflection.getCallerClass(4).getSimpleName() + " - " + message;
        }catch(Exception e){}
        //先输出到日志文件
        if (level.equals("error")) log.error(log_id + "::" + message);
        else if (level.equals("warn")) log.warn(log_id + "::" + message);
        else if (level.equals("info")) log.info(log_id + "::" + message);
        else log.debug(log_id + "::" + message);

        if (MemCache.getSystemParameter("logSaveDB")==null||!MemCache.getSystemParameter("logSaveDB").equals("true")) return;
        //保存到数据库
        if (logentity == null) {//新的日志id
            logentity=systemLogMapper.selectByPrimaryKey(log_id);
            if (logentity == null) {//防止重启logentity被重置
                logentity = new SystemLog();
                logentity.setLog_id(log_id);
                logentity.setData_id(dataId);
                logentity.setFun_id(funId);
                logentity.setUser_id(ShiroUtils.getUserId());
                logentity.setLog_level(level);
                logentity.setLog_time(new Date());
                logentity.setLog_content(message);
                systemLogMapper.insert(logentity);
            }
        }
        //写入明细
        SystemLogDetail detail = new SystemLogDetail();
        detail.setDetail_id(Long.toString(uidGenerator.getUID()));
        detail.setLog_id(log_id);
        detail.setData_id(dataId);
        detail.setFun_id(funId);
        detail.setUser_id(ShiroUtils.getUserId());
        detail.setLog_time(new Date());
        detail.setLog_content(message);
        detail.setLog_level(level);
        systemLogDetailMapper.insert(detail);
        if(levelMap.get(level)>levelMap.get(logentity.getLog_level())){
            logentity.setLog_level(level);
            logentity.setLog_content(message);
            systemLogMapper.updateByPrimaryKey(logentity);
        }
    }

    /**
     * 多模态下，多个session访问同一个日志线程
     * @param log_id
     * @param level
     * @param message
     * @param funId
     * @param dataId
     */
    public void setLogSession(String log_id, String level, String message, String funId, String dataId) {
        try {
            message = Reflection.getCallerClass(4).getSimpleName() + " - " + message;
        }catch(Exception e){}
        //先输出到日志文件
        if (level.equals("error")) log.error(log_id + "::" + message);
        else if (level.equals("warn")) log.warn(log_id + "::" + message);
        else if (level.equals("info")) log.info(log_id + "::" + message);
        else log.debug(log_id + "::" + message);

        if (MemCache.getSystemParameter("logSaveDB")==null||!MemCache.getSystemParameter("logSaveDB").equals("true")) return;
        //保存到数据库
        if(!log_id.equals(_sessionID)) {
            _sessionID=log_id;
            logentitySession = systemLogMapper.selectByPrimaryKey(log_id);
        }
        if (logentitySession == null) {//新的日志id
            logentitySession = new SystemLog();
            logentitySession.setLog_id(log_id);
            logentitySession.setData_id(dataId);
            logentitySession.setFun_id(funId);
            logentitySession.setUser_id(ShiroUtils.getUserId());
            logentitySession.setLog_level(level);
            logentitySession.setLog_time(new Date());
            logentitySession.setLog_content(message);
            systemLogMapper.insert(logentitySession);
        }
        //写入明细
        SystemLogDetail detail = new SystemLogDetail();
        detail.setDetail_id(Long.toString(uidGenerator.getUID()));
        detail.setLog_id(log_id);
        detail.setData_id(dataId);
        detail.setFun_id(funId);
        detail.setUser_id(ShiroUtils.getUserId());
        detail.setLog_time(new Date());
        detail.setLog_content(message);
        detail.setLog_level(level);
        systemLogDetailMapper.insert(detail);
        if(levelMap.get(level)>levelMap.get(logentitySession.getLog_level())){
            logentitySession.setLog_level(level);
            logentitySession.setLog_content(message);
            systemLogMapper.updateByPrimaryKey(logentitySession);
        }
    }



    public void info(String log_id,String message) {
        setLogSession(log_id,"info", message,"","");
    }

    public void warn(String log_id,String message) {
        setLogSession(log_id,"warn", message,"","");
    }

    public void error(String log_id,String message) {
        setLogSession(log_id,"error",message,"","");
    }

    public void debug(String log_id,String message) {
        setLogSession(log_id,"debug", message,"","");
    }


    public void error(String log_id,String message, Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        setLogSession(log_id,"error", message + stringWriter.toString(), "", "");
    }

    public void info(String log_id,String message, String funId, String dataId) {
        setLogSession(log_id,"info", message, funId, dataId);
    }

    public void warn(String log_id,String message, String funId, String dataId) {
        setLogSession(log_id,"warn", message, funId, dataId);
    }

    public void error(String log_id,String message, String funId, String dataId) {
        setLogSession(log_id,"error", message, funId, dataId);
    }

    public void debug(String log_id,String message, String funId, String dataId) {
        setLogSession(log_id,"debug", message, funId, dataId);
    }


    public void error(String log_id,String message, Exception e, String funId, String dataId) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        setLogSession(log_id,"error", message + stringWriter.toString(),  funId, dataId);
    }
}
