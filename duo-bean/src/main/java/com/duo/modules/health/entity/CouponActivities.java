package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="coupon_activities")
public class CouponActivities extends BaseEntity{ 

    @Id
     private String activities_id;//主键id

    private String project_id;//项目id

    private String auditing;//是否有效

    private String activities_name;//活动名称

    private String coupon_type;//券类型

    private java.util.Date apply_date;//审批日期

    private String coupon_name;//优惠券名称

    private String img_url;//优惠券图片

    private Integer coupon_num;//数量

    private Double coupon_price;//优惠金额

    private Double total_money;//最小可用金额

    private Integer future_days;//获得起几天

    private java.util.Date begin_time;//指定可用开始时间

    private java.util.Date end_time;//指定可用结束时间

    private String use_type;//叠加方式

    private String user_name;//审核人

    private String user_id;//审核人id

    private String company_name;//商户名称

    private String company_id;//商户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }