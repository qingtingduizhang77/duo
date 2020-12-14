package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api_return")
public class ToolApiReturn extends BaseEntity{ 

    @Id
     private String column_id;//主键id

    private String api_id;//api_id

    private String column_source;//字段来源

    private String column_code;//字段代号

    private String column_name;//字段名称

    private String column_type;//字段类型

    private String format_style;//样式转换

    private String format_regex;//样式转换正则表达式

    private String column_default;//默认值

    private String change_type;//转换方式

    private String data_control;//转换控件值

    private String sub_api_id;//子接口api

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }