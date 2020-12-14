package com.duo.modules.common.bean;

import com.duo.core.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class Pages<T> {

    private long pages=1L;
    private long size=1L;
    private long total=1L;
    private long current=1L;
    private List<T> records;

}
