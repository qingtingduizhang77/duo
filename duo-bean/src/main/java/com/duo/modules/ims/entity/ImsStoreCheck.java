package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_check")
public class ImsStoreCheck extends BaseEntity{ 

    @Id
     private String check_id;//主键id

    private String project_id;//项目id

    private String auditing;//盘点状态

    private String check_no;//盘点单号

    private java.util.Date check_date;//盘点日期

    private String user_name;//盘点人

    private String user_id;//盘点人id

    private java.util.Date audit_date;//审核日期

    private String audit_user_nane;//审核人

    private String audit_user_id;//审核人id

    private String house_code;//仓库号

    private String house_name;//仓库名称

    private String house_id;//仓库id

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