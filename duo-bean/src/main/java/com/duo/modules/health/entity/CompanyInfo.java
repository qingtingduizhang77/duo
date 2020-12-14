package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="company_info")
public class CompanyInfo extends BaseEntity{ 

    @Id
     private String company_id;//主键id

    private String project_id;//项目id

    private String auditing;//管理状态

    private String company_type;//商户类型

    private String company_name;//商户名称

    private String company_code;//商户编号

    private String company_sortname;//商户简称

    private String field_id;//field_id

    private String field_name;//区域名称

    private String address;//商户地址

    private String contract_user;//联系人

    private String contract_phone;//联系人电话

    private String lose_memo;//注销原因

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }