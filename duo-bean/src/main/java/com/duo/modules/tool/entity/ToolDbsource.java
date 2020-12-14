package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_dbsource")
public class ToolDbsource extends BaseEntity{ 

    @Id
     private String dbsource_id;//dbsource_id

    private Integer order_index;//排序

    private String auditing;//状态

    private String dbsource_type;//数据源类型

    private String dbsource_name;//数据源名称

    private String db_driver;//驱动类

    private String jdbc_url;//连接串

    private String user_name;//用户名

    private String user_password;//密码

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String load_status;//已加载

    private String record_flag;//是否删除

 }