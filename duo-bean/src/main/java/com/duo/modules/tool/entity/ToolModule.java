package com.duo.modules.tool.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="tool_module")
public class ToolModule extends BaseEntity{ 

    @Id
     private String module_id;//module_id

    private Integer order_index;//排序

    private String module_name;//模块名称

    private String project_id;//项目id

    private Integer module_level;//层级

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String is_show;//是否显示

    private String ico_img;//模块图标

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
        return "module_level";
    }
    public void setLevel(Integer level){
        module_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
    public String getTextColumn(){
        return "module_name";
    }
 }