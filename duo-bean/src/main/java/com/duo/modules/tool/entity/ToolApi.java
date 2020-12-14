package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api")
public class ToolApi extends BaseEntity{ 

    @Id
     private String api_id;//api_id

    private String project_id;//项目id

    private String project_name;//项目名称

    private String api_name;//接口名称

    private String api_version;//版本号

    private Integer order_index;//排序

    private String api_status;//状态

    private String api_type;//api类型

    private String api_url;//访问地址

    private String data_entity;//操作entity

    private String data_sql;//操作SQL/Class

    private String pre_sql;//执行前判断SQL

    private String pre_sql_error;//执行前判断SQL异常反馈

    private String post_sql1;//执行后更新SQL1

    private String post_sql2;//执行后更新SQL2

    private String dbsource_name;//数据源

    private String allow_time;//允许访问时间范围

    private String is_log;//记录日志?

    private String memo;//备注

    private String old_api_id;//old_api_id

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }