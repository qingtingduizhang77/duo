package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_move")
public class ImsStoreMove extends BaseEntity{ 

    @Id
     private String move_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String move_no;//移库单号

    private String move_type;//移库类型

    private java.util.Date move_date;//移库时间

    private String user_name;//移库人

    private String user_id;//移库人id

    private java.util.Date audit_date;//审核日期

    private String audit_user_nane;//审核人

    private String audit_user_id;//审核人id

    private String pre_house_code;//移出库仓库号

    private String pre_house_name;//移出库仓库

    private String pre_house_id;//移出库仓库id

    private Integer move_num;//移出库数量

    private Double move_money;//移出库金额

    private String house_code;//移入库仓库号

    private String house_name;//移入库仓库

    private String house_id;//移入库仓库id

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