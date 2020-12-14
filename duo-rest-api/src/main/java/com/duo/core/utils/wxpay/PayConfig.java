package com.duo.core.utils.wxpay;

import com.duo.core.utils.wxpay.sdk.IWXPayDomain;
import com.duo.core.utils.wxpay.sdk.WXPayConfig;
import com.duo.core.utils.wxpay.sdk.WXPayConstants;

import java.io.InputStream;

public class PayConfig extends WXPayConfig {

    @Override
    public String getAppID() {
        return PayConstant.appId;
    }

    @Override
    public String getMchID() {
        return PayConstant.mchId;
    }

    @Override
    public String getKey() {
        return PayConstant.mchKey;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }
}
