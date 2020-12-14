package com.duo.core;

import com.baidu.fsg.uid.UidGenerator;
import com.duo.Constants;
import com.duo.core.utils.ShiroUtils;
import com.duo.modules.system.entity.SystemLog;
import com.duo.modules.system.entity.SystemLogDetail;
import com.duo.modules.system.mapper.SystemLogDetailMapper;
import com.duo.modules.system.mapper.SystemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
@Component
public class LogCenterStatic {
    @Resource
    private LogCenter obj;


    private static LogCenter logcenter;

    @PostConstruct
    public void init() {
        logcenter=obj;
    }

    public static void info(String message) {
        logcenter.setLog("info", message,"","");
    }

    public static void warn(String message) {
        logcenter.setLog("warn", message,"","");
    }

    public static void error(String message) {
        logcenter.setLog("error",message,"","");
    }

    public static void debug(String message) {
        logcenter.setLog("debug", message,"","");
    }
 

    public static void error(String message, Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logcenter.setLog("error", message + stringWriter.toString(), "", "");
    }

    public static void info(String message, String funId, String dataId) {
        logcenter.setLog("info", message, funId, dataId);
    }

    public static void warn(String message, String funId, String dataId) {
        logcenter.setLog("warn", message, funId, dataId);
    }

    public static void error(String message, String funId, String dataId) {
        logcenter.setLog("error", message, funId, dataId);
    }

    public static void debug(String message, String funId, String dataId) {
        logcenter.setLog("debug", message, funId, dataId);
    }


    public static void error(String message, Exception e, String funId, String dataId) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logcenter.setLog("error", message + stringWriter.toString(),  funId, dataId);
    }


    public void info(String log_id,String message) {
        logcenter.setLog(log_id,"info", message,"","");
    }

    public void warn(String log_id,String message) {
        logcenter.setLog(log_id,"warn", message,"","");
    }

    public void error(String log_id,String message) {
        logcenter.setLog(log_id,"error",message,"","");
    }

    public void debug(String log_id,String message) {
        logcenter.setLog(log_id,"debug", message,"","");
    }


    public void error(String log_id,String message, Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logcenter.setLog(log_id,"error", message + stringWriter.toString(), "", "");
    }

    public void info(String log_id,String message, String funId, String dataId) {
        logcenter.setLog(log_id,"info", message, funId, dataId);
    }

    public void warn(String log_id,String message, String funId, String dataId) {
        logcenter.setLog(log_id,"warn", message, funId, dataId);
    }

    public void error(String log_id,String message, String funId, String dataId) {
        logcenter.setLog(log_id,"error", message, funId, dataId);
    }

    public void debug(String log_id,String message, String funId, String dataId) {
        logcenter.setLog(log_id,"debug", message, funId, dataId);
    }


    public void error(String log_id,String message, Exception e, String funId, String dataId) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logcenter.setLog(log_id,"error", message + stringWriter.toString(),  funId, dataId);
    }
}
