package com.duo.core.utils.wxpay;

import com.duo.core.utils.wxpay.sdk.WXPay;
import com.duo.core.utils.wxpay.sdk.WXPayUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WxPayUtil {

    public static String getPayLink(PayParam param) {
        Map<String, String> paraMap = new HashMap<String, String>();

        paraMap.put("appid", PayConstant.appId);
        paraMap.put("mch_id", PayConstant.mchId);
        paraMap.put("body", param.getPayBody());                           //商品类目
        paraMap.put("out_trade_no", param.getOrderNo());                   //商户订单号
        paraMap.put("spbill_create_ip", param.getPayIp());                 //请求ip
        paraMap.put("total_fee", param.getPayMoney());                     //支付金额，单位分
        paraMap.put("trade_type", "NATIVE");                               //字符类型：扫码支付
        paraMap.put("notify_url", PayConstant.notifyUrl);                 //回调地址
        paraMap.put("attach", new Date().getTime()+"");                    //自定义参数

        try {
            String s = WXPayUtil.generateSignature(paraMap, PayConstant.mchKey);  //签名
            paraMap.put("sign", s);
            PayConfig config = new PayConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> resp = wxpay.unifiedOrder(paraMap);
            System.out.println("返回map:" + resp.toString());
            return resp.get("code_url");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
