package com.duo.modules.health.model;


import lombok.Data;

@Data
public class DeviceNumber {

    private String device_code;//设备编号

    private String device_address;//设备位置

    private Integer current_number;//正在就诊号码

    private Integer waiting_number;//正在等待号码

    private Integer last_number;//最后等待号码

    private Integer device_status;//机器状态0：机器故障1：空闲2：问诊中3：消毒作业中

 }