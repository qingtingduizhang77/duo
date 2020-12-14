package com.duo.core;

import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * @author [ Yonsin ] on [2019/4/11]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class BaseEntity {

    @Transient
    private String add_userid;
    @Transient
    private Date add_date;
    @Transient
    private String modify_userid;
    @Transient
    private Date modify_date;
    @Transient
    private String record_flag;
    @Transient
    private String project_id;

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
        return "";
    }
    public void setLevel(Integer level){

    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
    public String getTextColumn(){
        return "";
    }
    //用于树型表，获取全称字段
    public String getFullNameColumn(){
        return "";
    }
}
