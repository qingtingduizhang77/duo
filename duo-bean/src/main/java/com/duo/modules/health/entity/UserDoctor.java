package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_doctor")
public class UserDoctor extends BaseEntity{ 

    @Id
     private String doctor_id;//主键id

    private String project_id;//项目id

    private String auditing;//是否发布

    private String doctor_type;//专家类型

    private String user_code;//系统帐号

    private String user_id;//系统帐号id

    private String doctor_name;//专家名称

    private String sex;//性别

    private String hospital_name;//所在医院

    private String special_area;//专业领域

    private String descript;//专家简介

    private String img_url;//照片url

    private String class_type;//值班方式

    private String id_card;//身份证号

    private String qualify_no;//资格证号

    private java.util.Date birth_date;//出生日期

    private java.util.Date from_date;//从医起始日期

    private String office_room;//出诊科室

    private String qualify_level;//资质级别

    private String mobile_no;//联系电话

    private String address;//联系地址

    private Double sum_money;//总收入

    private Double normal_price;//普通问诊价格

    private Double balance_money;//余额

    private Double special_price;//专家问诊价格

    private Double applying_money;//申请提现中

    private String memo;//备注

    private String add_userid;//创建人id

    private java.util.Date add_date;//创建时间

    private String modify_userid;//修改人id

    private java.util.Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String serv_status;//问诊标识 0不接诊  1可接诊

    private String hospital_id;//医院id

    private String office_room_id;//科室id
 }