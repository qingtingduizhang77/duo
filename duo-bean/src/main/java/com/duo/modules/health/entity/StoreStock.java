package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="store_stock")
public class StoreStock extends BaseEntity{ 

    @Id
     private String stock_id;//主键id

    private String project_id;//项目id

    private String stock_code;//仓库货架号

    private String stock_name;//仓库货架名

    private String bar_code;//条码号

    private String inplace_descript;//位置描述

    private Integer stock_level;//级别

    private String user_nane;//保管员

    private String user_id;//保管员id

    private String company_name;//商户名称

    private String company_id;//商户id

    private Double min_num;//最小库存量

    private Double max_num;//最大库存量

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    //用于树型表，返回级别字段名称
    public String getLevelColumn(){
      return "stock_level";
    }
    public void setLevel(Integer level){
      stock_level=level;
    }
    //用于树型表，返回显示值字段名称，返回2个字段（中间用,隔开）则会   字段1（字段2） 格式显示树值
     public String getTextColumn(){
      return "stock_name";
    }

 }