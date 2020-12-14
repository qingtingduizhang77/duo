package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_online")
public class SystemOnline extends BaseEntity{ 

    @Id
     private String login_id;//login_id

    private String project_id;//project_id

    private String last_viewurl;//最后访问功能

    private java.util.Date last_fleshtime;//最后访问时间

    private String user_id;//user_id

    private String dept_id;//dept_id

    private String dept_name;//部门管理

    private String user_name;//用户名称

    private java.util.Date login_time;//登录时间

    private String login_status;//状态

    private String login_ip;//登录IP

    private java.util.Date logout_time;//退出时间

    private String browser_type;//浏览器类型

 }