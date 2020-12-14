package com.duo.core;

import com.duo.modules.tool.entity.ToolApiUser;
import lombok.Data;

/**
 * @author [ Yonsin ] on [2020/9/10]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class APISessionBean {

    private String tokenID;
    private String ip;
    private String loginTime;
    private ToolApiUser toolApiUser;

}
