package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_role_fun")
public class SystemRoleFun extends BaseEntity{ 

    @Id
     private String det_id;//主键

    private String role_id;//角色id

    private String project_funid;//项目功能id

    private String fun_id;//功能id

    private String is_access;//访问权限

    private String event_edit;//编辑事件权限

    private String event_sign;//复核事件权限

    private String event_audit;//审批事件权限

    private String event_delete;//删除事件权限

    private String event_print;//打印事件权限

    private String dataright_sql;//数据权限SQL

    private String add_userid;//add_userid

    private java.util.Date add_date;//add_date

    private String modify_userid;//modify_userid

    private java.util.Date modify_date;//modify_date

    private String record_flag;//record_flag

 }