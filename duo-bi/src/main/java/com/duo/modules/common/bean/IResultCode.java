package com.duo.modules.common.bean;

import java.io.Serializable;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

public interface IResultCode extends Serializable {
    String getMessage();

    int getCode();
}