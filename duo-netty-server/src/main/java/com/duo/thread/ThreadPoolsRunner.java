package com.duo.thread;

import com.duo.MemCache;
import com.duo.core.utils.StringUtils;
import com.duo.thread.monitor.ThreadPoolsMonitorMain;
import com.duo.thread.monitor.ThreadPoolsOnAndOffLineMain;
import com.duo.thread.parse.ThreadPoolsParseMain;
import com.duo.thread.putdown.ThreadPoolsPutdownMain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author [ Yonsin ] on [2020/8/18]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

@Component
@Slf4j
public class ThreadPoolsRunner implements CommandLineRunner {

    /**
     * 任务调度开始
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings)  {
        log.info("Netty ==============后台服务启动开始"+ MemCache.getSystemParameter("runNetty"));
        try {
            //启动下发任务主线程
            String putdowNum=MemCache.getSystemParameter("putdowThreadNum");
            if(StringUtils.isEmpty(putdowNum))putdowNum="5";
            ThreadPoolsPutdownMain putdownMain=new ThreadPoolsPutdownMain();
            putdownMain.init(putdowNum);
            putdownMain.start();
            log.info("启动下发任务线程成功，线程数："+putdowNum);

            //启动解释任务主线程
            String parseNum=MemCache.getSystemParameter("parseThreadNum");
            if(StringUtils.isEmpty(parseNum))parseNum="10";
            ThreadPoolsParseMain parseMain=new ThreadPoolsParseMain();
            parseMain.init(parseNum);
            parseMain.start();
            log.info("启动解释任务线程成功，线程数："+parseNum);

            //启动上下线任务主线程
            String lineNum=MemCache.getSystemParameter("lineThreadNum");
            if(StringUtils.isEmpty(lineNum))lineNum="2";
            ThreadPoolsOnAndOffLineMain lineMain=new ThreadPoolsOnAndOffLineMain();
            lineMain.init(lineNum);
            lineMain.start();
            log.info("启动上下线任务线程成功，线程数："+lineNum);

            //启动监控线程
            ThreadPoolsMonitorMain monitorMain=new ThreadPoolsMonitorMain();
            monitorMain.start();
            log.info("启动监控线程成功!");


        }catch(Exception e) {
            log.error("Netty后台服务启动失败",e);
        }
        log.info("Netty后台服务==============启动完成");
    }


}
