package com.duo.modules.common.service.bean;

import lombok.Data;

/**
 * @author [ Yonsin ] on [2020/7/6]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public  class Node {
    private int top;
    private int left;
    private String name;
    private int width;
    private int height;
    private boolean alt;
    private String type;
}