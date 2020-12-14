package com.duo.core.utils;

public enum WzDeviceEnum {

    DEV_FAULT("0", "机器故障"),
    DEV_FREE("1", "空闲"),
    DEV_USED("2", "问诊中"),
    DEV_DISINFECT("3", "消毒作业中");

    private final String value;
    private final String desc;

    WzDeviceEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

}
