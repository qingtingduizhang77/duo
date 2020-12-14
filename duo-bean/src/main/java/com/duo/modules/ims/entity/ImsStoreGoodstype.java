package com.duo.modules.ims.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="ims_store_goodstype")
public class ImsStoreGoodstype extends BaseEntity{ 

    @Id
     private String type_id;//主键id

    private String project_id;//项目id

    private Integer type_level;//级别

    private String type_name;//分类名称

    private String full_name;//分类全称

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "type_level";
    }
    public void setLevel(Integer level){
      type_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "type_name";
    }

 }