package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_workflow_node")
public class ToolWorkflowNode extends BaseEntity{ 

    @Id
     private String node_id;//主键id

    private String wf_id;//wf_id

    private String number_sql;//计算待办数量sql

    private String node_no;//节点号

    private String column_json;//字段设置json

    private String node_name;//节点名称

    private String node_type;//节点类型

    private String fun_id;//功能id

    private String show_alluser;//显示所有人?

    private String allow_skip;//允许跳过经办人?

    private String time_maxdays;//最大等待天数

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }