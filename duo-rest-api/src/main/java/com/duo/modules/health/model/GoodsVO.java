package com.duo.modules.health.model;

import lombok.Data;

@Data
public class GoodsVO {
    private String service_id;//问诊id
    private String goods_id;//药品id
    private String goods_name;//药品名称
    private String goods_size;//药品规格
    private Integer buy_num;//购买量
    private String drug_usage;//药品用法
    private String unit;//单位
    private Double goods_price;//单价
    private Double goods_money;//金额
}
