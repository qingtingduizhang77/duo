package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_system_module")
public class ToolSystemModule extends BaseEntity{ 

    @Id
     private String system_id;//system_id

    private String system_name;//系统名称

    private Integer order_index;//排序

    private Integer system_level;//层级

    private String is_show;//是否显示

    private String memo;//备注

    private String add_userid;//add_userid

    private java.util.Date add_date;//add_date

    private String modify_userid;//modify_userid

    private java.util.Date modify_date;//modify_date

    private String record_flag;//数据标识

    private String ico_img;//模块图标

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "system_level";
    }
    public void setLevel(Integer level){
      system_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "system_name";
    }

 }