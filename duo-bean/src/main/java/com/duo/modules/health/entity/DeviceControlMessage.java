package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_control_message")
public class DeviceControlMessage extends BaseEntity{ 

    @Id
     private String message_id;//主键id

    private String control_id;//control_id

    private String device_id;//device_id

    private String message_type;//指令类型

    private java.util.Date plan_time;//计划执行时间

    private String auditing;//执行状态

    private String file_name;//升级包版本

    private String file_id;//file_id

    private String connect_message;//指令信息

    private String is_success;//是否执行成功

    private String call_backmessage;//反馈信息

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }