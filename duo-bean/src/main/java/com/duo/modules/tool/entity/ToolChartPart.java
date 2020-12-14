package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_chart_part")
public class ToolChartPart extends BaseEntity{ 

    @Id
     private String part_id;//主键id

    private String chart_id;//chart_id

    private String part_type;//图标类型

    private String part_name;//分类名称

    private String data_column;//数据字段

    private String yaxis_index;//对应y轴

    private String label_type;//样式类型

    private String is_stack;//是否层叠?

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }