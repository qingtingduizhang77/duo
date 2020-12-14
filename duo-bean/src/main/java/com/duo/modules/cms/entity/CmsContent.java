package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="cms_content")
public class CmsContent extends BaseEntity{ 

    @Id
     private String content_id;//主键id

    private String auditing;//状态

    private String content_title;//内容标题

    private String content_type;//内容分类

    private String content_keyword;//关键字

    private String teamplate_name;//内容模版

    private String teamplate_id;//内容模版id

    private String teamplate_version;//版本号

    private String content_design;//内容设计

    private String html_url;//html地址

    private String expre_data;//扩展字段Json

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