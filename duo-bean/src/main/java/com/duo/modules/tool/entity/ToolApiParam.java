package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api_param")
public class ToolApiParam extends BaseEntity{ 

    @Id
     private String param_id;//主键id

    private String api_id;//api_id

    private String param_source;//参数来源

    private String param_code;//参数代号

    private String param_name;//参数名称

    private String param_type;//参数类型

    private String is_null;//必填？

    private String check_regex;//格式校验正则表达式

    private String param_default;//默认值

    private String change_type;//转换方式

    private String data_control;//转换控件值

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }