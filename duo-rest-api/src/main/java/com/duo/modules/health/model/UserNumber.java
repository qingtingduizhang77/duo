package com.duo.modules.health.model;

import lombok.Data;
import java.util.Date;

@Data
public class UserNumber {

    private String member_id;//用户id

    private Integer u_number;//用户排队号

    private String num_date;//取号日期

    private String device_code;//机器编码

    private String device_address;//机器地址

    private Date create_time;//创建时间

    private Integer current_number; //正在就诊号码

    private Integer waiting_number; //正在等待号码

    private String device_id; //设备id

    private Boolean new_status;//是否新号

 }