package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_function_query")
public class ToolFunctionQuery extends BaseEntity{ 

    @Id
     private String query_id;//query_id

    private String fun_id;//fun_id

    private String query_name;//查询方案名称

    private String is_show;//是否显示

    private String is_default;//是否为默认查询

    private Integer order_index;//排序

    private String where_sql;//查询where语句

    private String memo;//备注

    private String add_userid;//add_userid

    private java.util.Date add_date;//add_date

    private String modify_userid;//modify_userid

    private java.util.Date modify_date;//modify_date

    private String record_flag;//记录标识

 }