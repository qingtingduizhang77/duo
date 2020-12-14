package com.duo.core.timer;

import com.duo.MemCache;
import com.duo.modules.common.service.TimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author [ Yonsin ] on [2019/4/12]
 * @Description： [ 实现CommandLineRunner接口，以达到开机启动执行 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Component
@Slf4j
public class TimerScheduleRunner implements CommandLineRunner {

    @Autowired
    private TimerService taskService;

    /**
     * 任务调度开始
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings)  {

       log.info("任务调度Timer==============任务调度启动开始"+MemCache.getSystemParameter("runTimer"));
       try {
           if("true".equals(MemCache.getSystemParameter("runTimer")))
                taskService.timingTask();
       }catch(Exception e) {
           log.error("Timer任务调度启动失败",e);
       }
        log.info("任务调度Timer==============任务调度启动完成");
    }
}
