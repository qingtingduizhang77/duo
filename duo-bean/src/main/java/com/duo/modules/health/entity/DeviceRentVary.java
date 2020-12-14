package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_rent_vary")
public class DeviceRentVary extends BaseEntity{ 

    @Id
     private String vary_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private java.util.Date vary_date;//变动日期

    private String rent_id;//rent_id

    private String rent_no;//出租单号

    private String apply_username;//提交人

    private String apply_userid;//提交人id

    private String check_username;//审核人

    private String check_userid;//审核人id

    private String org_device_name;//原设备名称

    private String org_device_code;//原设备编号

    private String org_device_id;//原设备id

    private String device_name;//新设备名称

    private String device_code;//新设备编号

    private String device_id;//新设备id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }