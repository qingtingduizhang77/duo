package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_select")
public class ToolSelect extends BaseEntity{ 

    @Id
     private String select_id;//select_id

    private Integer order_index;//排序

    private String select_code;//控件代号

    private String fun_id;//fun_id

    private String layout_url;//布局路径

    private String select_name;//控件名称

    private String memo;//备注

    private java.util.Date modify_date;//修改时间

    private String modify_userid;//修改人id

    private String project_id;//项目id

    private String record_flag;//数据标识

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

 }