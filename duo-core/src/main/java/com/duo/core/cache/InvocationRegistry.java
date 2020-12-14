package com.duo.core.cache;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 缓存方法注册接口 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public interface InvocationRegistry {

	void registerInvocation(Object invokedBean, Method invokedMethod, Object[] invocationArguments, Set<String> cacheNames);

}