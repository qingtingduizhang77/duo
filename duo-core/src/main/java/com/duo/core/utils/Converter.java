package com.duo.core.utils;

/**
 * 数据转换接口
 *
 * @author yonsin
 */
public interface Converter {
    /**
     * 进行转换操作
     *
     * @param obj 原对象
     * @return 转换后对象
     */
    public Object convert(Object obj);
}
