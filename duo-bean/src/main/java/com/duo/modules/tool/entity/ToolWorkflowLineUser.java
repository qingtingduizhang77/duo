package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_workflow_line_user")
public class ToolWorkflowLineUser extends BaseEntity{ 

    @Id
     private String noduser_id;//主键id

    private String wf_id;//wf_id

    private String line_id;//line_id

    private String user_id;//user_id

    private String user_name;//姓名

    private String dept_name;//默认部门

    private String dept_id;//dept_id

    private Integer order_index;//排序

    private String user_type;//人员类型

    private String is_group;//用户组?

    private String is_default;//是否默认

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }