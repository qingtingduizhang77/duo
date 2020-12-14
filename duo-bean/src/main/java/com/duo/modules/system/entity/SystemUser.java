package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_user")
public class SystemUser extends BaseEntity{ 

    @Id
     private String user_id;//user_id

    private String user_code;//登录号

    private String user_name;//用户名称

    private String user_password;//密码

    private String user_level;//用户级别

    private String manage_level;//管理级别

    private String user_sex;//性别

    private String user_salt;//密码salt

    private String dept_id;//部门id

    private String dept_code;//部门编号

    private String dept_name;//部门名称

    private java.util.Date login_time;//最近登录时间

    private String is_valid;//是否生效

    private java.util.Date create_date;//创建时间

    private String login_ip;//限制登录ip

    private String memo;//备注

    private String role_ids;//所属角色

    private String description;//个人描述

    private String user_img;//用户头像

    private String add_userid;//新增人

    private java.util.Date add_date;//新增时间

    private String modify_userid;//最后修改人

    private java.util.Date modify_date;//最后修改时间

    private String record_flag;//数据标记

    private String project_id;//project_id

 }