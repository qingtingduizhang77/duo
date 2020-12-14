package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_control")
public class DeviceControl extends BaseEntity{ 

    @Id
     private String control_id;//主键id

    private String device_id;//device_id

    private String message_type;//指令类型

    private java.util.Date plan_time;//计划执行时间

    private String auditing;//指令状态

    private String file_name;//升级包版本

    private String file_id;//file_id

    private Double file_size;//升级包大小

    private String is_success;//是否执行成功

    private String call_backmessage;//反馈信息

    private String apply_username;//提交人

    private String apply_userid;//提交人id

    private String check_username;//审核人

    private String check_userid;//审核人id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }