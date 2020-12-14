package com.duo.core.utils;

public enum CmdEnum {

    CMD_DEV_HEART("999", "心跳"),
    CMD_DEV_TEST("100", "连通测试"),
    CMD_DEV_PAUSE("101", "暂停"),
    CMD_DEV_STARTUP("102", "开机"),
    CMD_DEV_SHUTDOWN("103", "关机"),
    CMD_CUT_SCREEN("104", "截屏"),
    CMD_SYS_PARAM("105","获取系统参数"),
    CMD_NEW_STATUS("106","获取最新状态"),
    CMD_SYS_TIME("107","获取系统时间"),
    CMD_RESET_TIME("108","重置时间"),

    CMD_AD_UPDATE("201", "广告升级"),
    CMD_PRO_UPDATE("202", "程序升级"),

    CMD_GET_NUMBER("301", "扫码取号"),
    CMD_OPEN_DOR("302", "扫码开门"),
    SEND_WZ_RESULT("303","推送诊疗结果"),
    SEND_GOODS_LIST("304","推送诊疗药品"),
    CMD_DO_VISIT("305", "开始视频问诊"),
    CMD_REFRESH_NUM("306", "刷新排号队列"),
    CMD_PAY_LINK("307", "获取支付链接地址"),
    CMD_PAY_FINISH("308", "药品支付完成"),

    CMD_BLOOD_PRE("401", "测量血压"),
    CMD_ENDOSCOPE("402", "内窥镜"),
    CMD_ELE_AUS("403", "电子听诊"),
    CMD_HEAR_RATE("404", "测量心率"),
    ;
    private final String cmd_id;
    private final String cmd_msg;

    CmdEnum(final String value, final String desc) {
        this.cmd_id = value;
        this.cmd_msg = desc;
    }

    public String getValue() {
        return this.cmd_id;
    }

    public String getDesc() {
        return this.cmd_msg;
    }

}
