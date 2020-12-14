package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_workflow_line")
public class ToolWorkflowLine extends BaseEntity{ 

    @Id
     private String line_id;//主键id

    private String wf_id;//wf_id

    private String start_node;//起节点

    private String end_node;//目标节点

    private String line_text;//描述

    private String line_type;//线类型

    private String where_sql;//过滤条件

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }