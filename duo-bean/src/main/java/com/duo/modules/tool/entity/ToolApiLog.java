package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_api_log")
public class ToolApiLog extends BaseEntity{ 

    @Id
     private String log_id;//主键id

    private java.util.Date occur_date;//发生时间

    private String user_id;//user_id

    private String api_id;//api_id

    private String api_name;//接口名称

    private String tocken_id;//tocken_id

    private String post_parames;//提交参数

    private String log_content;//日志内容

    private String is_success;//是否成功

    private String source_ip;//来源ip

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }