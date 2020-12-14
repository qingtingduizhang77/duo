package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_report_area")
public class ToolReportArea extends BaseEntity{ 

    @Id
     private String area_id;//主键id

    private String report_id;//report_id

    private String auditing;//状态

    private Integer order_index;//排序

    private String area_type;//区域类型

    private String area_name;//区域名称

    private Integer page_size;//每页记录数

    private String area_postion;//区域范围

    private String is_main;//是否主区域

    private String data_source;//数据来源

    private String data_sql;//sql或class路径

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }