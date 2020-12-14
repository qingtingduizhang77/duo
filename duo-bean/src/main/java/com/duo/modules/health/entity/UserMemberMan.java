package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="user_member_man")
public class UserMemberMan extends BaseEntity{ 

    @Id
     private String man_id;//主键id

    private String project_id;//项目id

    private String member_id;//member_id

    private String man_name;//会员姓名

    private String sex;//性别

    private String nation;//民族

    private String marriage;//婚姻 0：未婚  1：已婚

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date both_date;//出生日期

    private String address;//住址

    private String mobile_no;//手机号

    private String e_mail;//邮箱

    private String id_card;//身份证号

    private String social_card;//社保卡号

    private String past_history;//既往病史

    private String drug_allergy;//药物过敏史

    private String genetic_disease;//遗传病史

    private String height;//身高

    private String weight;//体重

    private String memo;//备注

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date add_date;//创建时间

    private String modify_userid;//修改人id

    private Date modify_date;//修改时间

    private String record_flag;//数据标识

    private String u_default;//是否默认就诊人 0否 1是

 }