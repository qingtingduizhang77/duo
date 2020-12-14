package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_controler")
public class ToolControler extends BaseEntity{ 

    @Id
     private String controler_id;//controler_id

    private String controler_type;//类型

    private Integer order_index;//排序

    private String controler_name;//名称

    private String controler_url;//访问URL

    private String memo;//备注

    private String add_userid;//

    private java.util.Date add_date;//

    private String modify_userid;//

    private java.util.Date modify_date;//

    private String record_flag;//数据标识

 }