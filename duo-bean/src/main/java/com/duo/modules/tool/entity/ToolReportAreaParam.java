package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_report_area_param")
public class ToolReportAreaParam extends BaseEntity{ 

    @Id
     private String param_id;//主键id

    private String area_id;//area_id

    private String report_id;//report_id

    private String param_source;//参数来源

    private String param_code;//参数编号

    private Integer order_index;//排序

    private String param_name;//参数名称

    private String param_type;//参数类型

    private String control_type;//控件类型

    private String control_code;//控件代码

    private String control_sourcecols;//来源字段

    private String control_targetcols;//目标字段

    private String control_where;//控件where

    private String param_default;//默认值

    private String is_show;//是否显示

    private String print_postion;//打印位置

    private String print_format;//打印样式

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }