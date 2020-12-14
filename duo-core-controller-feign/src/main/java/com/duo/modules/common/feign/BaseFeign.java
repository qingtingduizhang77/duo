package com.duo.modules.common.feign;

import com.duo.core.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
* 
* <p>Title: TestFeign.java</p>
* @author Carlton
* @date 2019年4月29日 上午9:26:36
*/
@FeignClient(name = "duo-service-provider")
public interface BaseFeign {


}
