package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="coupon_list")
public class CouponList extends BaseEntity{ 

    @Id
     private String coupon_id;//主键id

    private String activities_id;//activities_id

    private String auditing;//使用状态

    private String coupon_type;//券类型

    private String coupon_code;//优惠券编号

    private String coupon_name;//优惠券名称

    private String img_url;//优惠券图片

    private Double coupon_price;//优惠金额

    private Double total_money;//最小可用金额

    private String use_type;//叠加方式

    private java.util.Date begin_time;//可用开始时间

    private java.util.Date end_time;//可用结束时间

    private java.util.Date use_time;//使用时间

    private String order_no;//订单号id

    private String member_name;//所属用户

    private String member_id;//所属用户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }