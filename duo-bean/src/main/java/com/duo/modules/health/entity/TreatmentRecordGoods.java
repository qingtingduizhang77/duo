package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "treatment_record_goods")
public class TreatmentRecordGoods extends BaseEntity {

    @Id
    private String det_id;//主键id

    private String service_id;//问诊id

    private String project_id;//项目id

    private String goods_id;//goods_id

    private String goods_name;//药品名称

    private String goods_size;//药品规格

    private String unit;//单位

    private Integer buy_num;//购买量

    private String drug_usage;//药品用法

    private Double goods_price;//单价

    private Double goods_money;//金额

    private String memo;//备注

    private String add_userid;//创建人id

    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

}