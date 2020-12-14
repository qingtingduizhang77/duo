package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_role_user")
public class SystemRoleUser extends BaseEntity{ 

    @Id
     private String det_id;//主键

    private String role_id;//角色id

    private String user_id;//用户id

    private String add_userid;//add_userid

    private java.util.Date add_date;//add_date

    private String modify_userid;//modify_userid

    private java.util.Date modify_date;//modify_date

    private String record_flag;//record_flag

 }