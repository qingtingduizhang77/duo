package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="advert_content")
public class AdvertContent extends BaseEntity{ 

    @Id
     private String content_id;//内容id

    private String project_id;//项目id

    private String auditing;//发布状态

    private String content_title;//标题

    private java.util.Date publish_time;//发表时间

    private String summary;//摘要

    private String content_type;//内容分类

    private String content_body;//广告内容

    private String suit_place;//适用广告位

    private String edit_username;//编辑人

    private String edit_userid;//编辑人id

    private String check_username;//审批人

    private String check_userid;//审批人id

    private String company_name;//广告主

    private String company_id;//广告主id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }