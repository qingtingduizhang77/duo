package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="advert_place")
public class AdvertPlace extends BaseEntity{ 

    @Id
     private String place_id;//主键id

    private String project_id;//项目id

    private String palce_code;//广告位编号

    private Integer place_level;//级别

    private String place_name;//广告位名称

    private String place_version;//版本号

    private String place_type;//广告位类型

    private String place_size;//广告位规格

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "place_level";
    }
    public void setLevel(Integer level){
      place_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "place_name";
    }

 }