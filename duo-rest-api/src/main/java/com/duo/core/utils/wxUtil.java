package com.duo.core.utils;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class wxUtil {

    public static RestTemplate restTemplate = new RestTemplate();

    public static String httpRequest(String url, HttpMethod method, String JsonStr) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonStr, headers);
        return restTemplate.exchange(url, method, httpEntity, String.class).getBody();
    }

    /**
     * 获取授权凭证
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return WeiXinAouth2Token
     * wat = new WeiXinOauth2Token();
     * wat.setAccessToken    (jsonObject.getString("access_token"));
     * wat.setExpiresIn    (jsonObject.getInt("expires_in"));
     * wat.setRefreshToken    (jsonObject.getString("refresh_token"));
     * wat.setOpenId        (jsonObject.getString("openid"));
     * wat.setScope        (jsonObject.getString("scope"));
     */
    public static String getWxOauthStr(String appId, String appSecret, String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
        return httpRequest(url, HttpMethod.GET, null);
    }

    /**
     * 刷新网页授权凭证
     *
     * @param appId
     * @param refreshToken
     * @return WeiXinAouth2Token
     */
    public static String refreshWxOauthStr(String appId, String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appId + "&refresh_token=" + refreshToken + "&grant_type=refresh_token";
        return httpRequest(url, HttpMethod.GET, null);
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return SNSUserInfo
     * snsUserInfo = new SNSUserInfo();
     * // 用户的标识
     * snsUserInfo.setOpenId(jsonObject.getString("openid"));
     * // 昵称
     * snsUserInfo.setNickname(jsonObject.getString("nickname"));
     * // 性别（1是男性，2是女性，0是未知）
     * snsUserInfo.setSex(jsonObject.getInt("sex"));
     * // 用户所在国家
     * snsUserInfo.setCountry(jsonObject.getString("country"));
     * // 用户所在省份
     * snsUserInfo.setProvince(jsonObject.getString("province"));
     * // 用户所在城市
     * snsUserInfo.setCity(jsonObject.getString("city"));
     * // 用户头像
     * snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
     * // 用户特权信息
     * snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
     */
    public static String getWxUserInfoStr(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        return httpRequest(url, HttpMethod.GET, null);
    }

    public static void main(String[] args)  {
        String openId = "ouYzq0Htv1iPUmXPWVZ5ZgsX4oAw";
        String token ="35_3bzeTNIdemIEoCEPtU0bRxBj4fcGNJUM836W_8I71OdCnXecPQo4Q81mxB_SaBx5j-SiyfmVW_TlKouK0xNLH25aWRl-O6zRmZc4GawdF7I";
//        String info = getWxUserInfoStr(token,openId);
//        Wxuser wxuser = JwUserAPI.getWxuser(token, openId);
//        System.out.println(wxuser.getNickname());

    }
}
