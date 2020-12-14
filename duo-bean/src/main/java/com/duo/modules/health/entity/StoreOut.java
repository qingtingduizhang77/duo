package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="store_out")
public class StoreOut extends BaseEntity{ 

    @Id
     private String out_id;//主键id

    private String project_id;//项目id

    private String out_no;//出库单号

    private String auditing;//出库状态

    private String out_type;//出库类型

    private java.util.Date out_date;//出库时间

    private String user_nane;//出库人

    private String user_id;//出库人id

    private String stock_name;//出库仓库

    private String stock_id;//出库仓库id

    private Integer out_num;//出库数量

    private Double out_money;//出库金额

    private String company_name;//商户名称

    private String company_id;//商户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }