package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api_user_rule")
public class ToolApiUserRule extends BaseEntity{ 

    @Id
     private String accect_id;//主键id

    private String user_id;//user_id

    private String user_name;//用户名称

    private Double each_num;//折算扣除次数

    private String api_id;//api_id

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }