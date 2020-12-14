package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_project")
public class ToolProject extends BaseEntity{ 

    @Id
     private String project_id;//project_id

    private Integer order_index;//排序

    private String project_code;//项目代号

    private String project_name;//项目名称

    private String project_version;//版本号

    private String manager_name;//项目负责人

    private String sent_date;//发布时间

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//是否删除

    private String auditing;//项目状态

 }