package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="cms_program")
public class CmsProgram extends BaseEntity{ 

    @Id
     private String program_id;//主键id

    private String auditing;//状态

    private String program_code;//节目编号

    private String program_name;//节目名称

    private String program_version;//版本号

    private String use_devicetype;//适用机型

    private String teamplate_id;//模板id

    private String teamplate_name;//栏目模版

    private String teamplate_version;//版本号

    private String html_url;//html地址

    private String package_url;//离线包地址

    private String expre_data;//扩展字段Json

    private java.util.Date upload_date;//提交时间

    private String dept_name;//部门

    private String dept_id;//dept_id

    private String audit_user;//审核人

    private java.util.Date audit_date;//审核时间

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }