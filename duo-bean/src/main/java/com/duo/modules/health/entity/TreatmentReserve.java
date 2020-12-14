package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="treatment_reserve")
public class TreatmentReserve extends BaseEntity{ 

    @Id
     private String reserve_id;//主键id

    private String reserve_no; //

    private String project_id;//项目id

    private String auditing;//订单状态// 预约号码状态0：等待叫号 1：正在叫号  2：超时过号 3：正在就诊 4：已问诊

    private String pay_status;//支付状态

    private String device_name;//设备名称

    private String device_code;//设备编号

    private String device_id;//设备id

    private String doctor_name;//医生名称

    private String doctor_id;//医生id

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date reserve_date;//预约日期

    private String reserve_time;//预约时间段

    private String reserve_source;//订单来源

    private Date pay_time;//付款时间

    private String back_status;//退款状态

    private Date back_time;//退款时间

    private String pay_type;//付款方式

    private String pay_id;//收单方id

    private Double pay_money;//付款金额

    private Double order_money;//订单金额

    private Double discount_money;//优惠金额

    private String member_name;//购买会员

    private String member_id;//购买会员id

    private String company_name;//商户名称

    private String company_id;//商户id

    private String id_card;//身份证号

    private String social_card;//社保卡号

    private String mobile_no;//手机号

    private String memo;//备注

    private String resourcedoctor_id;//resourcedoctor_id

    private String resourcedevice_id;//resourcedevice_id

    private String add_userid;//创建人id

    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

 }