package com.duo.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;

/**
 * @author [ Yonsin ] on [2019/4/12]
 * @Description： [ spring自带的@Scheduled注解实现的定时任务 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
//@Component
//@EnableScheduling
@Slf4j
public class SchedulerTask {

    private int count = 0;

//    @Scheduled(cron = "*/1 * * * * ?")
    private void process() {
        log.info("this is scheduler task runing  " + (count++));
        log.info("当前时间：" + LocalTime.now());
    }
}
