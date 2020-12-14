package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_goods")
public class ImsStoreGoods extends BaseEntity{ 

    @Id
     private String goods_id;//主键id

    private String project_id;//项目id

    private String auditing;//发布状态

    private String bar_code;//物品条码号

    private String goods_code;//物品号

    private String goods_name;//物品名称

    private String type_name;//物品分类

    private String type_id;//物品分类id

    private String goods_size;//规格

    private Double goods_price;//零售价

    private Double last_price;//最近进货价

    private String unit;//单位

    private String goods_descript;//物品介绍

    private String img_url;//图片

    private String expiry_date;//有效期

    private String is_free;//是否赠品

    private Double min_num;//最小库存量

    private Double max_num;//最大库存量

    private String purchase_period;//采购周期

    private String brand;//品牌

    private String factory;//生产厂家

    private String param_name1;//参数1名称

    private String param_value1;//参数1值

    private String param_name2;//参数2名称

    private String param_value2;//参数2值

    private String param_name3;//参数3名称

    private String param_value3;//参数3值

    private String param_name4;//参数4名称

    private String param_value4;//参数4值

    private String company_name;//公司名称

    private String company_id;//公司id

    private String dept_id;//部门id

    private String dept_name;//部门名称

    private String add_username;//创建人

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }