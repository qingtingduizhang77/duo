package com.duo.modules.common.web;


import com.duo.core.BaseController;
import com.duo.core.BaseEntity;
import com.duo.core.vo.PageResultSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ Redis服务器管理 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
@RequestMapping("/base")
public class RedisController extends BaseController {


    @ResponseBody
    @RequestMapping("/list")
    public PageResultSet<BaseEntity> list() {

        return null;
    }



}
