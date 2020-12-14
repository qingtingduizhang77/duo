package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_project_system")
public class ToolProjectSystem extends BaseEntity{ 

    @Id
     private String project_systemid;//project_systemtid

    private String system_id;//system_id

    private String project_id;//project_id

    private String system_name;//系统名称

    private String main_layout;//首页模版

    private Integer order_index;//排序

    private String main_home;//首页

    private String memo;//备注

    private String add_userid;//创建人id

    private String is_show;//是否显示

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//是否删除

 }