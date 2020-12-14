package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_log")
public class SystemLog extends BaseEntity{ 

    @Id
     private String log_id;//log_id

    private java.util.Date log_time;//发生时间

    private String log_level;//日志级别

    private String fun_id;//funId

    private String user_id;//user_id

    private String data_id;//data_id

    private String log_content;//日志内容

 }