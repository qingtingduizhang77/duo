package com.duo.modules.health.model;

import lombok.Data;

@Data
public class OrderVO {
    private Double pay_money;//付款金额
    private Integer goods_num;//商品数量
    private String order_no;//订单编号
    private String pay_status;//支付状态
}
