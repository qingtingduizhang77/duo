package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_fault")
public class DeviceFault extends BaseEntity{ 

    @Id
     private String fault_id;//主键id

    private String project_id;//项目id

    private String auditing;//管理状态

    private String fault_name;//告警名称

    private java.util.Date begin_time;//停机开始时间

    private String fault_message;//告警信息

    private java.util.Date end_time;//停机结束时间

    private String device_id;//device_id

    private String deal_username;//处理人

    private String deal_userid;//处理人id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }