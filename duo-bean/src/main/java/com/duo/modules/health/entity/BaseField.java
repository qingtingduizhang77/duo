package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="base_field")
public class BaseField extends BaseEntity{ 

    @Id
     private String field_id;//field_id

    private String field_code;//区域编码

    private String field_name;//区域名称

    private String field_fullname;//区域全称

    private Integer order_index;//排序

    private Integer field_level;//层级

    private String project_id;//项目id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "field_level";
    }
    public void setLevel(Integer level){
      field_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "field_name";
    }

    //用于树型表，获取全称字段
     public String getFullNameColumn(){
      return "field_fullname";
    }

 }