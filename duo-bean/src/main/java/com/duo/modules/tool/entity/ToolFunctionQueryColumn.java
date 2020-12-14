package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_function_query_column")
public class ToolFunctionQueryColumn extends BaseEntity{ 

    @Id
     private String column_id;//column_id

    private String query_id;//query_id

    private String column_code;//字段代号

    private String column_name;//字段名称

    private String column_default;//默认值

    private Integer order_index;//排序

    private String is_show;//页面显示

    private String column_type;//字段类型

    private String control_name;//控件名称

    private String control_parames;//控件参数

    private String place_holder;//提示信息

    private String control_where;//控件参数-where

    private String control_source;//控件参数-源

    private String control_target;//控件参数-目标

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }