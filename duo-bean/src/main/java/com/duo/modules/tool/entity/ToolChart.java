package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_chart")
public class ToolChart extends BaseEntity{ 

    @Id
     private String chart_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String chart_type;//图表模版

    private String chart_title;//标题名称

    private String chart_subtitle;//子标题名称

    private String fix_colors;//预设颜色

    private String x_name;//x轴-名称

    private String x_valuetype;//x轴-取值方式

    private String x_value;//x轴-值参数

    private String y1_name;//y轴1-名称

    private String y1_valuetype;//y轴1-取值方式

    private Double y1_min;//y轴1-最小值

    private Double y1_max;//y轴1-最大值

    private Double y1_interval;//y轴1-步长

    private String y1_unit;//y轴1-后缀单位

    private String y2_name;//y轴2-名称

    private String y2_valuetype;//y轴2-取值方式

    private Double y2_min;//y轴2-最小值

    private Double y2_max;//y轴2-最大值

    private Double y2_interval;//y轴2-步长

    private String y2_unit;//y轴2-后缀单位

    private String has_legend;//是否显示legend过滤

    private String has_tool;//是否显示工具栏

    private String data_from;//数据来自？

    private String fun_id;//fun_id

    private String sql_statement;//sql语句

    private String data_source;//数据源

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }