package com.duo.quartz.job;

import com.duo.core.LogCenter;
import com.duo.core.utils.ExecClassMethodUtil;
import com.duo.modules.tool.entity.ToolTimer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Component("duoJob01")
@Transactional
public class DuoJob01 {
    @Resource
    public LogCenter log;

    public void execute(ToolTimer timer) {

        Object bl= ExecClassMethodUtil.invokeMethod(ExecClassMethodUtil.creatObject("com.duo.core.utils.ExecClassMethodUtil"), "xxx", "测试");
        System.out.println(bl);
    }
}
