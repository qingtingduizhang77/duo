package com.duo.modules.health.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class WxUserVO {
    private String member_name;
    private String phone;
    private String openid;
    private String nickname;
    private String gender;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
}
