package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="goods_list")
public class GoodsList extends BaseEntity{ 

    @Id
     private String goods_id;//主键id

    private String project_id;//项目id

    private String auditing;//发布状态

    private String bar_code;//商品条码号

    private String goods_name;//商品名称

    private String type_name;//商品分类

    private String type_id;//商品分类id

    private String goods_size;//规格

    private Double retail_price;//零售价

    private Double machine_price;//设备价

    private Double eshop_price;//电商价

    private Double trade_price;//批发价

    private String unit;//单位

    private String goods_descript;//商品介绍

    private String img_url;//图片

    private String expiry_date;//有效期

    private String is_free;//是否赠品

    private String is_oct;//是否处方药

    private String is_tcm;//是否中药成分

    private String sick_label;//治疗疾病标签

    private String factory;//生产厂家

    private String is_certification;//是否需要实名

    private String temperature_demand;//温度要求

    private String humidity_demand;//湿度要求

    private String company_name;//商户名称

    private String company_id;//商户id

    private String add_username;//创建人

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }