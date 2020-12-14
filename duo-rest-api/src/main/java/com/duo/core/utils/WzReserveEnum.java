package com.duo.core.utils;

public enum WzReserveEnum {

    WAITING_CALL("0", "等待叫号"),
    CALLING_NUM("1", "正在叫号"),
    PASS_CALL("2", "超时过号"),
    VISIT_NUM("3", "正在问诊"),
    FINISH_NUM("4", "问诊结束");

    private final String value;
    private final String desc;

    WzReserveEnum(final String value, final String desc) {
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
