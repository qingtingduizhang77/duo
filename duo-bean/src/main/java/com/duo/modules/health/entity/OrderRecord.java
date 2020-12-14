package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="order_record")
public class OrderRecord extends BaseEntity{ 

    @Id
     private String order_id;//主键id

    private String project_id;//项目id

    private String auditing;//订单状态

    private String out_status;//出货状态

    private String pay_status;//支付状态

    private String service_status;//售后状态

    private String order_no;//订单编号

    private java.util.Date order_time;//下单时间

    private java.util.Date pay_time;//付款时间

    private String back_status;//退款状态

    private java.util.Date back_time;//退款时间

    private String pay_type;//付款方式

    private String pay_id;//收单方id

    private Double pay_money;//付款金额

    private Double order_money;//订单金额

    private Double discount_money;//优惠金额

    private String member_name;//购买会员

    private String member_id;//购买会员id

    private Integer goods_num;//商品数量

    private String device_id;//device_id

    private String company_name;//商户名称

    private String company_id;//商户id

    private String id_card;//身份证号

    private String social_card;//社保卡号

    private String mobile_no;//手机号

    private String is_invoice;//是否开发票

    private String e_mail;//发票接收邮箱

    private String invoice_url;//电子发票链接

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }