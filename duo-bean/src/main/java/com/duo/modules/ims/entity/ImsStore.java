package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store")
public class ImsStore extends BaseEntity{ 

    @Id
     private String store_id;//主键id

    private String in_id;//in_id

    private String goods_id;//goods_id

    private String in_type;//入库类型

    private java.util.Date in_date;//入库时间

    private String user_name;//入库人

    private String user_id;//入库人id

    private java.util.Date audit_date;//审核日期

    private String audit_user_nane;//审核人

    private String audit_user_id;//审核人id

    private String house_code;//仓库号

    private String house_name;//仓库名称

    private String house_id;//仓库id

    private String provider_name;//供应商名称

    private String provider_id;//供应商id

    private String delivery_man;//送货人

    private String delivery_mobile;//送货人电话

    private String collect_no;//采购单号

    private String collect_id;//采购单id

    private String company_name;//公司名称

    private String company_id;//公司id

    private String dept_id;//部门id

    private String dept_name;//部门名称

    private String stock_id;//stock_id

    private String stock_code;//仓库货架号

    private String stock_name;//仓库货架名

    private Double in_price;//单价

    private Integer in_num;//数量

    private String batch_no;//批次号

    private java.util.Date create_date;//生产日期

    private java.util.Date expiry_date;//有效期至

    private Integer useable_num;//剩余数量

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }