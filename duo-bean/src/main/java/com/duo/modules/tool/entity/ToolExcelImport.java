package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_excel_import")
public class ToolExcelImport extends BaseEntity{ 

    @Id
     private String import_id;//import_id

    private Integer order_index;//排序

    private String fun_id;//fun_id

    private String fun_name;//功能名称

    private String auditing;//状态

    private String excel_template;//excel模版

    private Integer head_index;//标题行号

    private String post_detsql1;//逐条导入后执行sql1

    private String post_detsql2;//逐条导入后执行sql2

    private String post_sql1;//导入后执行sql1

    private String post_sql2;//导入后执行sql2

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date modify_date;//修改时间

    private String modify_userid;//修改人id

    private String project_id;//项目id

    private String record_flag;//数据标识

    private String file_id;//file_id

    private java.util.Date add_date;//创建时间

 }