package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_report")
public class ToolReport extends BaseEntity{ 

    @Id
     private String report_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private Integer order_index;//排序

    private String fun_id;//fun_id

    private String system_id;//system_id

    private String module_id;//module_id

    private String report_name;//报表名称

    private String report_type;//报表类型

    private String print_type;//打印格式

    private String report_file;//打印模版

    private Integer page_width;//纸张宽度mm

    private Integer page_height;//纸张高度mm

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }