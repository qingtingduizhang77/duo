package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_paychannel")
public class SystemPaychannel extends BaseEntity{ 

    @Id
     private String paychannel_id;//主键id

    private String project_id;//项目id

    private String auditing;//状态

    private String pay_type;//支付方式

    private String pay_descript;//账户描述

    private String pay_usercode;//账号

    private String pay_password;//密码

    private String company_name;//商户名称

    private String company_id;//商户id

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

 }