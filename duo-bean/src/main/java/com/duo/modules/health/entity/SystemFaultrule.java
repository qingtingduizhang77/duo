package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_faultrule")
public class SystemFaultrule extends BaseEntity{ 

    @Id
     private String rule_id;//主键id

    private String project_id;//项目id

    private String auditing;//是否生效

    private String fault_name;//告警名称

    private String fault_descript;//描述

    private String device_type;//设备型号

    private String type_id;//设备种类id

    private String compute_sql;//判断条件

    private String warning_type;//通知方式

    private String sms_template;//通知模版

    private String is_repair;//通知维护？

    private String is_operation;//通知运营？

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }