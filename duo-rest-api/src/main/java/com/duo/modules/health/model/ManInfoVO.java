package com.duo.modules.health.model;

import com.duo.modules.health.entity.UserMember;
import lombok.Data;

import java.util.Date;

@Data
public class ManInfoVO {
    private String man_id;//就诊人id
    private String man_name;//就诊人名称
    private String id_card;//身份证号
    private Date both_date;//出生日期
    private String sex;//性别
    private String nation;//民族
    private Integer marriage;//婚姻 0：未婚  1：已婚
    private String mobile_no;//手机号
    private UserMember userMember;
}
