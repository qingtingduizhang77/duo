package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_function_event_sql")
public class ToolFunctionEventSql extends BaseEntity{ 

    @Id
     private String eventsql_id;//eventsql_id

    private String event_id;//eventdet_id

    private Integer order_index;//排序

    private String event_type;//事件类型

    private String source_funid;//源funid

    private String bo_function;//执行SQL

    private String target_funid;//目标funid

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String source_sql;//源sql

    private String modify_userid;//修改人id

    private String target_sql;//目标sql

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }