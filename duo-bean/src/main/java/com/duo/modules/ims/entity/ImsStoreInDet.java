package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_in_det")
public class ImsStoreInDet extends BaseEntity{ 

    @Id
     private String det_id;//主键id

    private String in_id;//in_id

    private String goods_id;//goods_id

    private String stock_id;//stock_id

    private String stock_code;//仓库货架号

    private String stock_name;//仓库货架名

    private Double in_price;//单价

    private Double in_num;//数量

    private Double in_money;//金额

    private String batch_no;//批次号

    private java.util.Date create_date;//生产日期

    private java.util.Date expiry_date;//有效期至

    private Double useable_num;//剩余数量

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }