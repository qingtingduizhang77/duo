package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_report_area_column")
public class ToolReportAreaColumn extends BaseEntity{ 

    @Id
     private String column_id;//主键id

    private String area_id;//area_id

    private String report_id;//report_id

    private String column_source;//字段来源

    private Integer order_index;//排序

    private String column_code;//字段代号

    private String column_name;//字段名称

    private String compute_formula;//计算公式

    private String null_default;//空值默认

    private String print_postion;//打印位置

    private String print_format;//打印样式

    private String combo_code;//下拉控件代号

    private String is_show;//是否显示

    private Integer column_width;//列宽

    private String background_color;//背景颜色

    private String font_color;//颜色

    private Integer font_weight;//字体粗细

    private String is_italic;//是否斜体

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }