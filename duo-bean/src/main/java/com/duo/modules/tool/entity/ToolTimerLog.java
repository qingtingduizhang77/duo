package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_timer_log")
public class ToolTimerLog extends BaseEntity{ 

    private String log_content;//日志内容

    @Id
     private String log_id;//主键id

    private java.util.Date occur_date;//发生时间

    private String timer_id;//timer_id

    private String timer_no;//定时器编号

    private String timer_name;//定时器名称

    private String is_success;//执行状态

    private String source_ip;//执行ip

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }