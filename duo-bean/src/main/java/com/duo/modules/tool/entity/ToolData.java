package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_data")
public class ToolData extends BaseEntity{ 

    @Id
    private String data_id;//data_id

     private java.util.Date add_date;//创建时间

    private String add_userid;//创建人id

    private String data_color;//颜色

    private Integer data_level;//层级

    private String data_text;//显示值

    private String data_value;//真实值

    private String memo;//备注

    private java.util.Date modify_date;//修改时间

    private String modify_userid;//修改人id

    private Integer order_index;//排序

    private String project_id;//项目id

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
        return "data_level";
    }
    public void setLevel(Integer level){
        data_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
    public String getTextColumn(){
        return "data_text";
    }
 }