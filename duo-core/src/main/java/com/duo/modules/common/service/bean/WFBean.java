package com.duo.modules.common.service.bean;

import lombok.Data;

import java.util.Map;

/**
 * @author [ Yonsin ] on [2020/7/6]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class WFBean {
    private String title;
    private  Map<String, Node> nodes;
    private  Map<String, Area> areas;
    private  Map<String, Line> lines;
    private int initNum;

}
