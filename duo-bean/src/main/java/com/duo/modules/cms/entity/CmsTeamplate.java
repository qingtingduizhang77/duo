package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="cms_teamplate")
public class CmsTeamplate extends BaseEntity{ 

    @Id
     private String teamplate_id;//主键id

    private String auditing;//状态

    private String teamplate_name;//模版名称

    private String teamplate_url;//模版路径

    private String teamplate_version;//版本号

    private String teamplate_type;//模版类型

    private String use_descript;//适用描述

    private String teamplate_design;//模版设计图

    private String screen_ratio;//分辨率

    private String dept_name;//部门

    private String dept_id;//dept_id

    private String user_name;//提交人

    private java.util.Date upload_date;//提交时间

    private String audit_user;//审核人

    private java.util.Date audit_date;//审核时间

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }