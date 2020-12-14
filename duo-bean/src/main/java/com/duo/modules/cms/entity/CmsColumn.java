package com.duo.modules.cms.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="cms_column")
public class CmsColumn extends BaseEntity{ 

    @Id
     private String column_id;//主键id

    private String auditing;//状态

    private String column_code;//栏目编号

    private String column_name;//栏目名称

    private String column_version;//版本号

    private Integer column_level;//栏目级别

    private String teamplate_id;//模板id

    private String teamplate_name;//栏目模版

    private String teamplate_version;//版本号

    private String html_url;//html地址

    private String expre_data;//扩展字段Json

    private String dept_name;//部门

    private String dept_id;//dept_id

    private String user_name;//提交人

    private java.util.Date upload_date;//提交时间

    private String audit_user;//审核人

    private java.util.Date audit_date;//审核时间

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "column_level";
    }
    public void setLevel(Integer level){
      column_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "column_name";
    }

 }