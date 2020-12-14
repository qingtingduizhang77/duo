package com.duo.modules.common.service;

import com.duo.core.BaseService;
import com.duo.core.timer.TimerFactory;
import com.duo.core.utils.StringUtils;
import com.duo.modules.tool.entity.ToolTimer;
import com.duo.modules.tool.mapper.ToolTimerMapper;
import com.duo.core.enums.JobOperateEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author [ Yonsin ] on [2019/4/12]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
//@Transactional
public class TimerService extends BaseService {

    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ToolTimerMapper toolTimerMapper;

    public void timingTask() throws SchedulerException {

        //查询数据库是否存在需要定时的任务
        List<ToolTimer> jobs = toolTimerMapper.selectAll();
        if (jobs != null) {
            jobs.forEach(this::addJob);
        }
        //启动所有任务
        startAllJob();
    }

    /**
     * 添加任务
     * @param job
     */
    public void addJob(ToolTimer job) {
        try {
            log.info("Timer "+job.getTimer_name()+" is starting....");
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getTimer_name())
                    .withSchedule(CronScheduleBuilder.cronSchedule(StringUtils.emptyToDefault(job.getTimer_schedule(),"0/2 * * * * ?")))
                    .startNow()
                    .build();
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(TimerFactory.class)
                    .withIdentity(job.getTimer_name())
                    .build();
            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put("duoJob", job);
            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.info("Timer "+job.getTimer_name()+" start ERROR!");
            throw new RuntimeException(e);
        }
    }

    public void operateJob(JobOperateEnum jobOperateEnum, ToolTimer job) throws SchedulerException {
        JobKey jobKey = new JobKey(job.getTimer_name());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            //抛异常
        }
        switch (jobOperateEnum) {
            case START:
                scheduler.resumeJob(jobKey);
                break;
            case PAUSE:
                scheduler.pauseJob(jobKey);
                break;
            case DELETE:
                scheduler.deleteJob(jobKey);
                break;
        }
    }

    public void startAllJob() throws SchedulerException {
        scheduler.start();
    }

    public void pauseAllJob() throws SchedulerException {
        scheduler.standby();
    }
}
