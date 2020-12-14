package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_message")
public class SystemMessage extends BaseEntity{ 

    @Id
     private String message_id;//主键id

    private String sent_userid;//发送人id

    private String request_userid;//收件人id

    private java.util.Date sent_time;//发送时间

    private String message_status;//发送状态

    private String message_title;//标题

    private String message_content;//消息内容

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }