package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="comment_message")
public class CommentMessage extends BaseEntity{ 

    @Id
     private String message_id;//主键id

    private String project_id;//项目id

    private String auditing;//留言状态

    private java.util.Date message_time;//留言时间

    private String message_content;//留言内容

    private String source_type;//留言来源

    private String source_id;//来源id

    private String reply_content;//回复内容

    private String reply_username;//回复人

    private String reply_userid;//回复人id

    private java.util.Date reply_time;//回复时间

    private String last_message_id;//上一条留言id

    private String is_sms;//是否短信通知

    private String member_name;//留言用户

    private String member_id;//留言用户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }