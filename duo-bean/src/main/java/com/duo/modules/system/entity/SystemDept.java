package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_dept")
public class SystemDept extends BaseEntity{ 

    @Id
     private String dept_id;//dept_id

    private String dept_code;//部门编号

    private String dept_name;//部门名称

    private String dept_leader;//部门领导

    private String dept_introduce;//部门职责

    private Integer dept_level;//层级

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String project_id;//项目id

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "dept_level";
    }
    public void setLevel(Integer level){
      dept_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "dept_name";
    }

 }