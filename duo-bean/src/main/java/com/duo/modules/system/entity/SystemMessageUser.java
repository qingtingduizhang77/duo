package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_message_user")
public class SystemMessageUser extends BaseEntity{ 

    @Id
     private String keu_id;//主键id

    private String message_id;//message_id

    private String user_id;//接收人id

    private String record_status;//阅读状态

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }