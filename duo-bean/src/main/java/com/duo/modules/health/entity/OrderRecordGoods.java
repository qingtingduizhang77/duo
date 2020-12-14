package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="order_record_goods")
public class OrderRecordGoods extends BaseEntity{ 

    @Id
     private String det_id;//主键id

    private String order_id;//order_id

    private String goods_id;//goods_id

    private Integer goods_num;//商品数量

    private String out_status;//出货状态

    private String cargo_code;//出货货道号

    private String cargo_id;//cargo_id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }