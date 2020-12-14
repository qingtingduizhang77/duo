package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_function_column")
public class ToolFunctionColumn extends BaseEntity{ 

    @Id
     private String column_id;//column_id

    private String fun_id;//fun_id

    private String group_name;//分组

    private String column_name;//字段编号

    private String column_statement;//字段语句

    private Integer order_index;//排序

    private String column_comment;//字段名称

    private String column_type;//字段类型


    private Integer column_width;//字段宽度

    private String auto_code;//自动编码?

    private String pre_code;//编码前缀

    private Integer code_length;//编码长度

    private String compute_sql;//编码计算公式

    private String is_addedit;//新增可编辑?

    private String is_modifyedit;//修改可编辑?

    private String is_null;//必填?

    private String is_unique;//唯一?

    private String unique_where;//唯一判断条件

    private String control_name;//控件名称

    private String default_value;//默认值

    private String display_style;//显示格式

    private Integer max_length;//最大字符长度

    private String blur_js;//鼠标离开触发

    private String memo;//备注

    private String control_where;//控件参数-where

    private String control_source;//控件参数-源

    private String control_target;//控件参数-目标

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String is_total;//自动汇总

    private String is_show;//是否Grid显示

 }