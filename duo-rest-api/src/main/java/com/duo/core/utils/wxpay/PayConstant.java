package com.duo.core.utils.wxpay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PayConstant {

    /**
     * 开发者ID
     */
    public static String appId;
    @Value("${wxpay.appId}")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 商户号
     */
    public static String mchId;
    @Value("${wxpay.mchId}")
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }


    /**
     * API密钥
     */
    public static String mchKey;
    @Value("${wxpay.mchKey}")
    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    /**
     * 统一下单-通知链接
     */
    public static String notifyUrl;
    @Value("${wxpay.notifyUrl}")
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
