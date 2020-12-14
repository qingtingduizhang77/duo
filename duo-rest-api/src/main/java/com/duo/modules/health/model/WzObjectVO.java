package com.duo.modules.health.model;

import lombok.Data;

import java.util.Date;

@Data
public class WzObjectVO {
    private String service_id;//问诊id
    private String service_no;//问诊流水号
    private Date start_time;//诊疗开始时间
    private String device_code;//设备编号
    private String device_address;//设备地址
    private String doctor_name;//专家名称
    private String hospital_name;//所在医院
    private String office_room;//出诊科室
    private Date add_date;//创建时间
    private Date end_time;//诊疗结束时间
    private String service_status;//诊疗状态
}
