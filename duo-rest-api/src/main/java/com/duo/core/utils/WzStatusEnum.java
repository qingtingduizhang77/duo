package com.duo.core.utils;

public enum WzStatusEnum {

    WAITING_VISIT("1", "等待医生接诊"),
    WAITING_PAY("2", "等待支付"),
    VIDEO_VISIT("3", "视频问诊中"),
    WAITING_CHECK("4", "药品审核中"),
    FINISH_VISIT("5", "完成问诊"),
    CANCEL_VISIT("6", "取消问诊");


    private final String value;
    private final String desc;

    WzStatusEnum(final String value, final String desc) {
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
