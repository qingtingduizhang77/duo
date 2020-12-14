package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_excel_import_column")
public class ToolExcelImportColumn extends BaseEntity{ 

    @Id
     private String column_id;//column_id

    private Integer order_index;//排序

    private String column_name;//字段名称

    private String column_comment;//字段描述

    private String column_type;//字段类型

    private String col_index;//来源列号

    private String change_type;//转换类型

    private String control_name;//下拉控件名

    private String value_type;//取值方式

    private String default_value;//默认值

    private String is_null;//是否必填

    private String memo;//备注

    private String import_id;//import_id

    private String add_userid;//创建人id

    private java.util.Date modify_date;//修改时间

    private String modify_userid;//修改人id

    private String project_id;//项目id

    private String record_flag;//数据标识

    private java.util.Date add_date;//创建时间

 }