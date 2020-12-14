package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_workflow_node_column")
public class ToolWorkflowNodeColumn extends BaseEntity{ 

    @Id
     private String nodecol_id;//主键id

    private String wf_id;//wf_id

    private String node_id;//node_id

    private String column_id;//column_id

    private String column_name;//字段编号

    private Integer order_index;//排序

    private String column_comment;//字段名称

    private String column_type;//字段类型

    private String is_edit;//可编辑?

    private String is_null;//必填?

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }