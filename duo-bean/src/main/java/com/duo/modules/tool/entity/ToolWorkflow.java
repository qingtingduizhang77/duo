package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_workflow")
public class ToolWorkflow extends BaseEntity{ 

    @Id
     private String wf_id;//主键id

    private String wf_name;//流程名称

    private String wf_code;//流程编号

    private String wf_type;//流程类型

    private String wf_state;//状态

    private String project_id;//项目id

    private String module_id;//模块id

    private String fun_id;//功能id

    private String wf_map;//流程图

    private String note_fun;//note功能对应

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }