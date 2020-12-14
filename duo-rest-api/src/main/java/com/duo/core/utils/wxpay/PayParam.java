package com.duo.core.utils.wxpay;

import lombok.Data;

@Data
public class PayParam {
    String orderNo;
    String payMoney;
    String payBody;
    String payIp;
}
