package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_announcement")
public class SystemAnnouncement extends BaseEntity{ 

    @Id
     private String announcement_id;//主键id

    private String sent_userid;//发送人id

    private String request_userid;//收件人id

    private java.util.Date sent_time;//发送时间

    private java.util.Date end_time;//有效期

    private String announcement_status;//发送状态

    private String announcement_title;//公告标题

    private String announcement_content;//公告内容

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }