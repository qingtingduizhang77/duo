package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_in")
public class ImsStoreIn extends BaseEntity{ 

    @Id
     private String in_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String in_no;//入库单号

    private String in_type;//入库类型

    private java.util.Date in_date;//入库时间

    private String user_name;//入库人

    private String user_id;//入库人id

    private java.util.Date audit_date;//审核日期

    private String audit_user_nane;//审核人

    private String audit_user_id;//审核人id

    private String house_code;//入库仓库号

    private String house_name;//入库仓库

    private String house_id;//入库仓库id

    private Integer in_num;//入库数量

    private Double in_money;//入库金额

    private String provider_name;//供应商名称

    private String provider_id;//供应商id

    private String delivery_man;//送货人

    private String delivery_mobile;//送货人电话

    private String purchase_no;//采购单号

    private String purchase_id;//采购单id

    private String company_name;//公司名称

    private String company_id;//公司id

    private String dept_id;//部门id

    private String dept_name;//部门名称

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }