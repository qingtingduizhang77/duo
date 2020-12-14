package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="device_rent")
public class DeviceRent extends BaseEntity{ 

    @Id
     private String rent_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String rent_no;//出租单号

    private java.util.Date rent_date;//办理日期

    private java.util.Date begin_time;//出租开始时间

    private java.util.Date end_time;//出租到期时间

    private String apply_username;//提交人

    private String apply_userid;//提交人id

    private String company_name;//租用商户

    private String company_id;//租用商户id

    private String check_username;//审核人

    private String check_userid;//审核人id

    private String settle_type;//结算方式

    private Double per_price;//月租金或费率

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }