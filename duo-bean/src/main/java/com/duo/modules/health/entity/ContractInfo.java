package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="contract_info")
public class ContractInfo extends BaseEntity{ 

    @Id
     private String contract_id;//主键id

    private String project_id;//项目id

    private String auditing;//合同状态

    private String contract_code;//合同编号

    private java.util.Date contract_date;//合同签订日期

    private String contract_name;//合同名称

    private String provider_code;//供应商编号

    private String provider_name;//供应商名称

    private Double contract_money;//合同总额(元)

    private String sup_user;//供方经办人

    private Double price;//设备总价(元)

    private String contract_descript;//合同概要

    private String dept_name;//管理部门

    private String dept_id;//管理部门id

    private String user_name;//授权人

    private String user_id;//授权人id

    private String provider_id;//供应商id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }