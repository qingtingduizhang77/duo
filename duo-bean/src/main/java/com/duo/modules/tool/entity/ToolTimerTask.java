package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_timer_task")
public class ToolTimerTask extends BaseEntity{ 

    @Id
     private String task_id;//主键id

    private String timer_id;//timer_id

    private String task_type;//任务类型

    private String is_transaction;//事务控制?

    private String task_name;//任务名称

    private Integer order_index;//排序

    private String service_id;//模型服务id

    private String service_name;//模型服务名称

    private String request_parames;//传入参数

    private String exec_sql;//执行SQL

    private String exec_class;//执行Class

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }