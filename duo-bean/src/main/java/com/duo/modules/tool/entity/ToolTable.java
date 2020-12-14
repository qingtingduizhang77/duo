package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_table")
public class ToolTable extends BaseEntity{ 

    @Id
     private String table_id;//主键id

    private String project_id;//项目id

    private Integer order_index;//排序

    private String table_name;//表名

    private String table_comment;//表描述

    private String table_type;//表类型

    private Integer table_level;//层级

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//记录标识

    private String is_tree;//是否树型

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "table_level";
    }
    public void setLevel(Integer level){
      table_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "table_name,table_comment";
    }

 }