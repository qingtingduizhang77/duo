package com.duo.modules.common.service.bean;

import lombok.Data;

/**
 * @author [ Yonsin ] on [2020/7/6]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class Area {
    private String type;
    private String from;
    private String to;
    private String name;
    private boolean dash;
}
