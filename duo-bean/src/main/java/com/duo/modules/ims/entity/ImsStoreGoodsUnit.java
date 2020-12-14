package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_goods_unit")
public class ImsStoreGoodsUnit extends BaseEntity{ 

    @Id
     private String key_id;//主键id

    private String goods_id;//goods_id

    private String project_id;//项目id

    private String in_unit;//入库单位

    private String out_unit;//出库单位

    private Double change_num;//换算比例(1:N)

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }