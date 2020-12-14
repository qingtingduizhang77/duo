package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="total_device")
public class TotalDevice extends BaseEntity{ 

    @Id
     private String total_id;//主键id

    private String device_id;//device_id

    private String total_month;//统计月份

    private Integer day_order_num;//当日订单数

    private Integer month_order_num;//当月订单数

    private Double day_order_money;//当日销售额

    private Double month_order_money;//当月销售额

    private Integer order_num;//累计订单数

    private Double order_money;//累计销售额

    private Integer settle_order_num;//待结算订单数

    private Double settle_order_money;//待结算金额

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }