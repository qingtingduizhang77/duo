package com.duo.modules.api.web;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
//@EnableEurekaClient
@CrossOrigin
@RestController
@RequestMapping("/api")
@Api(tags="测试类",value="测试类")
public class APIController {

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value="【PC端】提交订单", notes="提交一组识别的标签id，返回生成的订单详情")
    @RequestMapping(value = "/test/{id}", method = RequestMethod.POST, produces  = "application/json;charset=UTF-8")
    public Integer test(@PathVariable Integer id){
        System.out.println(id);
        return id;
    }
    /**
     * 登录用户
     * @param json
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody JSONObject json) {

        String username = json.getString("username");
        String password = json.getString("password");

        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "OK";
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // token.setRememberMe(true);
        subject.login(token);
        return "OK";
    }

    /**
     * 注销用户
     * @return
     */
    @RequestMapping(value = "/logout",method={RequestMethod.POST})
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "OK";
    }



    @RequestMapping(value = "/test")
    public String  test(String parames) {
        return "test"+parames;
    }


    @RequestMapping("/hi")
    public String hello(String name) {
        return restTemplate.getForObject("http://eureka-server/app/test?parames=" + name, String.class);
    }

}