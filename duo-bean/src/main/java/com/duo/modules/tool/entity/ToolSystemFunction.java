package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_system_function")
public class ToolSystemFunction extends BaseEntity{ 

    @Id
     private String system_funid;//system_funid

    private String fun_id;//fun_id

    private String fun_name;//功能名称

    private String db_source;//数据源

    private String system_id;//系统模块id

    private Integer order_index;//排序

    private String is_show;//是否显示

    private String ico_img;//图标

    private String url_parames;//个性参数

    private String memo;//备注

    private String add_userid;//add_userid

    private java.util.Date add_date;//add_date

    private String modify_userid;//modify_userid

    private java.util.Date modify_date;//modify_date

    private String record_flag;//记录标识

 }