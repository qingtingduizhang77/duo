package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_portal")
public class ToolPortal extends BaseEntity{ 

    @Id
     private String portal_id;//主键id

    private String project_id;//项目id

    private String auditing;//发布状态

    private Integer order_index;//排序

    private String portal_name;//Portal名称

    private String portal_version;//版本号

    private String portal_type;//portal类型

    private String portal_map;//布局图

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }