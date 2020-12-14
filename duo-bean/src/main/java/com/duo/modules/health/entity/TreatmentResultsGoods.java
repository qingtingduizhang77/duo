package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="treatment_results_goods")
public class TreatmentResultsGoods extends BaseEntity{ 

    @Id
     private String det_id;//主键id

    private String results_id;//诊疗结果id

    private String goods_id;//主键id

    private String goods_name;//商品名称

    private String type_name;//商品分类

    private String type_id;//商品分类id

    private String goods_size;//规格

    private String bar_code;//商品条码号

    private Double machine_price;//设备价

    private String unit;//单位

    private String goods_descript;//商品介绍

    private String img_url;//图片

    private String expiry_date;//有效期

    private String is_free;//是否赠品

    private String is_oct;//是否处方药

    private String is_tcm;//是否中药成分

    private String sick_label;//治疗疾病标签

    private String project_id;//项目id

    private String memo;//备注

    private String add_userid;//创建人id

    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

 }