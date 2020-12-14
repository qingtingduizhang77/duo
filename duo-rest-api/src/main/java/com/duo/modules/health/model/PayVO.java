package com.duo.modules.health.model;

import lombok.Data;

@Data
public class PayVO {
    String wz_order_no;
    Double wz_pay_money;
    String wz_pay_status;
    String goods_order_no;
    Double goods_pay_money;
    String goods_pay_status;
}
