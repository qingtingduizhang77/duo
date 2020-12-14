package com.duo.quartz.job;

import com.duo.core.LogCenter;
import com.duo.modules.tool.entity.ToolTimer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component("duoJob02")
@Transactional
public class DuoJob02 {
    @Resource
    public LogCenter log;

    public void execute(ToolTimer timer) {
       log.info(timer.getTimer_id(),"-------------------TestJob02任务执行开始-------------------");
        System.out.println(new Date());
        log.info(timer.getTimer_id(),"-------------------TestJob02任务执行结束-------------------");
    }
}