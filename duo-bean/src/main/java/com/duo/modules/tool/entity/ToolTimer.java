package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_timer")
public class ToolTimer extends BaseEntity{ 

    @Id
     private String timer_id;//主键id

    private String project_id;//项目id

    private Integer order_index;//排序

    private String timer_no;//定时器编号

    private String timer_name;//定时器名称

    private String auditing;//状态

    private String timer_period;//周期类型

    private Integer period_num;//周期数

    private String server_first;//首选执行服务器ip

    private String timer_schedule;//定时策略

    private String server_ips;//备选执行服务器ip

    private java.util.Date last_time;//上次执行时间

    private java.util.Date run_time;//下次执行时间

    private String db_source;//数据源

    private Integer timeout_num;//超时时间(分钟)

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }