package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_service")
public class ToolService extends BaseEntity{ 

    @Id
     private String service_id;//主键id

    private String project_id;//项目id

    private String auditing;//是否发布

    private String service_name;//服务名称

    private String service_version;//版本号

    private String service_type;//服务类型

    private String service_map;//服务流程图

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }