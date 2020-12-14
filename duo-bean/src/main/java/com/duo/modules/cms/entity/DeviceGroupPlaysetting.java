package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_group_playsetting")
public class DeviceGroupPlaysetting extends BaseEntity{ 

    @Id
     private String setting_id;//主键id

    private String group_id;//group_id

    private String setting_type;//策略名称

    private String week_day;//星期

    private String program_name;//节目名称

    private String program_id;//节目id

    private String paly_time;//节目时间段

    private String program_version;//版本号

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }