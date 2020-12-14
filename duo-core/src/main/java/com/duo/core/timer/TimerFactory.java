package com.duo.core.timer;

import com.duo.core.utils.SpringUtil;
import com.duo.modules.tool.entity.ToolTimer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

public class TimerFactory implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //获取调度数据
        ToolTimer  job = (ToolTimer) jobExecutionContext.getMergedJobDataMap().get("duoJob");

        try {
            //获取对应的Bean
            Object object = SpringUtil.getBean(job.getTimer_no());
            if(object!=null) {
                //利用反射执行对应方法
                Method method = object.getClass().getMethod("execute", ToolTimer.class);
                method.invoke(object, job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
