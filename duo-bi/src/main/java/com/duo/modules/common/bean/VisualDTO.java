package com.duo.modules.common.bean;

import com.duo.modules.tool.entity.BiVisual;
import com.duo.modules.tool.entity.BiVisualConfig;
import lombok.Data;

import java.io.Serializable;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

@Data
public class VisualDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 可视化主信息
     */
    private BiVisual visual;

    /**
     * 可视化配置信息
     */
    private BiVisualConfig config;

}
