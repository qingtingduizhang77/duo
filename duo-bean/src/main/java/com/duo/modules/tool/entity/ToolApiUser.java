package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api_user")
public class ToolApiUser extends BaseEntity{ 

    @Id
     private String user_id;//user_id

    private String user_code;//用户编号

    private String user_name;//用户名称

    private String user_password;//密码

    private String user_status;//状态

    private java.util.Date valid_date;//有效期

    private String limit_ip;//指定访问ip

    private Double has_num;//可访问次数

    private Double used_num;//已访问次数

    private String last_ip;//最近登录ip

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }