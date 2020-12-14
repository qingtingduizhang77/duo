package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_role")
public class SystemRole extends BaseEntity{ 

    @Id
     private String role_id;//role_id

    private String role_name;//角色名称

    private String memo;//备注

    private String system_id;//系统id

    private String project_id;//项目id

    private String add_userid;//

    private java.util.Date add_date;//

    private String modify_userid;//

    private java.util.Date modify_date;//

    private String record_flag;//

 }