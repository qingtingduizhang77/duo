package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_total")
public class ImsStoreTotal extends BaseEntity{ 

    @Id
     private String total_id;//主键id

    private String goods_id;//goods_id

    private String total_month;//统计月份

    private String auditing;//状态

    private String house_code;//仓库号

    private String house_name;//仓库名称

    private String house_id;//仓库id

    private String company_name;//公司名称

    private String company_id;//公司id

    private String dept_id;//部门id

    private String dept_name;//部门名称

    private Double store_price;//库存单价

    private Double pre_num;//期初数量

    private Double pre_money;//期初金额

    private Double in_num;//入库数量

    private Double in_money;//入库金额

    private Double out_num;//出库数量

    private Double out_money;//出库金额

    private Double post_num;//期末数量

    private Double post_money;//期末金额

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }