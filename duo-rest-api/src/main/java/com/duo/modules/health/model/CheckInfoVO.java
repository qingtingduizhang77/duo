package com.duo.modules.health.model;

import lombok.Data;

@Data
public class CheckInfoVO {
    private String service_id;
    private String height;//身高
    private String weight;//体重
    private String temperature;//体温
    private String heart_rate;//心率
    private String sbp;//血压(收缩压)
    private String dbp;//血压(舒张压)
    private String endoscope;//窥镜检查
}
