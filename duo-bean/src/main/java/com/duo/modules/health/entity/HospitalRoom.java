package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="hospital_room")
public class HospitalRoom extends BaseEntity{ 

    @Id
     private String room_id;//room_id

    private String room_code;//科目编码

    private String room_name;//科目名称

    private Integer order_index;//排序

    private Integer room_level;//层级

    private String project_id;//项目id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "room_level";
    }
    public void setLevel(Integer level){
      room_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "room_name";
    }

 }