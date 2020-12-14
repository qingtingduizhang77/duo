package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_out")
public class ImsStoreOut extends BaseEntity{ 

    @Id
     private String out_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String out_no;//出库单号

    private String out_type;//出库类型

    private java.util.Date out_date;//出库时间

    private String user_name;//出库人

    private String user_id;//出库人id

    private java.util.Date audit_date;//审核日期

    private String audit_user_nane;//审核人

    private String audit_user_id;//审核人id

    private String house_code;//出库仓库号

    private String house_name;//出库仓库

    private String house_id;//出库仓库id

    private Integer out_num;//出库数量

    private Double out_money;//出库金额

    private String company_name;//公司名称

    private String company_id;//公司id

    private String dept_id;//部门id

    private String dept_name;//部门名称

    private String apply_user_nane;//领用人

    private String apply_user_id;//领用人id

    private String apply_season;//领用用途

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }