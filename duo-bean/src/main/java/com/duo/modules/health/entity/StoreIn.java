package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="store_in")
public class StoreIn extends BaseEntity{ 

    @Id
     private String in_id;//主键id

    private String project_id;//项目id

    private String auditing;//入库单状态

    private String in_type;//入库类型

    private String in_no;//入库单号

    private java.util.Date in_date;//入库时间

    private String user_nane;//入库人

    private String user_id;//入库人id

    private String stock_name;//入库仓库

    private String stock_id;//入库仓库id

    private Integer in_num;//入库数量

    private Double in_money;//入库金额

    private String company_name;//商户名称

    private String company_id;//商户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }