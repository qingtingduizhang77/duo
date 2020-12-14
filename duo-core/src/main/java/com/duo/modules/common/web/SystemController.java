package com.duo.modules.common.web;

import com.duo.MemCache;
import com.duo.core.PasswordHelper;
import com.duo.core.exception.ErrorSystemAccountException;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.SystemUserService;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.tool.entity.ToolProject;
import com.duo.modules.tool.entity.ToolProjectSystem;
import com.duo.modules.tool.entity.ToolSystemModule;
import com.duo.modules.tool.mapper.ToolProjectMapper;
import com.duo.modules.tool.mapper.ToolProjectSystemMapper;
import com.duo.modules.tool.mapper.ToolSystemModuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duo.core.BaseController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description： 登录
 * @author [ Yonsin ] on [2019年1月24日下午5:31:13]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Controller
@Slf4j
public class SystemController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private ToolProjectMapper toolProjectMapper;
    @Autowired
    private ToolProjectSystemMapper toolProjectSystemMapper;
    @Autowired
    private ToolSystemModuleMapper toolSystemModuleMapper;

    @RequestMapping("/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        log.info("begin to login");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登陆失败次数过多";
        }else if(ErrorSystemAccountException.class.getName().equals(exceptionClassName)) {
            error = "账号无该系统登录权限！";
        }else if("kaptchaValidateFailed".equals(exceptionClassName)) {
            error = "验证码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        //非SaaS项目，取第一个可用项目信息
        String isSaaS=  MemCache.getSystemParameter("isSaaS");
        System.out.println("isSaaS=============="+isSaaS);
        if(isSaaS!=null&&isSaaS.equals("false")) {
            ToolProject query=new ToolProject();
            query.setAuditing("1");
            ToolProject obj=toolProjectMapper.selectOne(query);
            if(obj!=null) {
                ToolSystemModule system = new ToolSystemModule();
                ToolProjectSystem ps = new ToolProjectSystem();
                ps.setProject_id(obj.getProject_id());
                List<ToolProjectSystem> lssystem = toolProjectSystemMapper.select(ps);
                StringBuffer systemList = new StringBuffer("");

                for (ToolProjectSystem sys : lssystem) {
                    ToolSystemModule o = toolSystemModuleMapper.selectByPrimaryKey(sys.getSystem_id());
                    systemList.append("<option value=\"" + o.getSystem_id() + "\"  " + (sys.getIs_show() != null && sys.getIs_show().equals("1") ? "selected" : "") + " >" + o.getSystem_name() + "</option>");
                }

                model.addAttribute("projectID", obj.getProject_id());
                model.addAttribute("systemList", systemList);
            }
        }
        return "login";

    }

    @RequestMapping("/main")
    public String main() throws Exception {
        log.info("begin to main");
        return "/system/main";
    }

    @GetMapping
    @RequestMapping("/system/page-system-role")
    public String page_system_role(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "system/page-system-role";
    }

    /**
     * 修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/system/changePassword")
    public Result<?> changePassword(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String userId=(String)requestMap.get("userId");
        String userCode=(String)requestMap.get("userCode");
        String oldPasswordBase64=(String)requestMap.get("oldPassword");//不存在则为管理员重设密码
        String newPasswordBase64=(String)requestMap.get("newPassword");
        //加密传输解密
        byte[] oldPasswordByte = new byte[0];
        byte[] newPasswordByte = new byte[0];
        try {
          if(oldPasswordBase64!=null&&!oldPasswordBase64.equals(""))  oldPasswordByte = Base64.decodeBase64(oldPasswordBase64.getBytes("UTF-8"));
            newPasswordByte = Base64.decodeBase64(newPasswordBase64.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oldPassword = new String(oldPasswordByte);//获得解密后的值
        String newPassword = new String(newPasswordByte);//获得解密后的值
        //密码规则判断，不能与旧密码一样和不能为空及强度要求等在页面判断即可

        SystemUser user;
        if(userId!=null){
            user=systemUserService.getUser(userId);
        }else {
            SystemUser record = new SystemUser();
            record.setUser_code(userCode);
            user=systemUserService.getUser(record);
        }
        if(user==null){
            return Result.failure("500","未找到该用户！");
        }
        if(oldPasswordBase64!=null&&!oldPasswordBase64.equals(""))  {
            String oldPasswordSalt = passwordHelper.getEncryptPassword(user.getUser_code(), user.getUser_salt(), oldPassword);
            if (!user.getUser_password().equals(oldPasswordSalt)) {
                return Result.failure("500", "原密码错误，修改失败！");
            }
        }
        String newPasswordSalt=passwordHelper.getEncryptPassword(user.getUser_code(),user.getUser_salt(),newPassword);
        user.setUser_password(newPasswordSalt);
        systemUserService.setUser(user);

        return Result.success();
    }
    @ResponseBody
    @RequestMapping("/system/rolefunction")
    public PageResultSet<Map<String,Object>> list(@RequestParam("funId") String funId, HttpServletRequest request) throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return  systemUserService.getFunctionPage(funId,requestMap);
    }
    /**
     * 查找系统通知信息
     */
    @ResponseBody
    @RequestMapping("/system/getMessage")
    public List getMessage(){
        String userId= ShiroUtils.getUserId();


        return null;
    }


    /**
     * 查找系统公告信息
     */
    @ResponseBody
    @RequestMapping("/system/getAnnouncement")
    public List getAnnouncement(){
        String userId= ShiroUtils.getUserId();


        return null;
    }



    /**
     * 查找系统待办信息
     */
    @ResponseBody
    @RequestMapping("/system/getTaskList")
    public List getTaskList(){
        String userId= ShiroUtils.getUserId();


        return null;
    }
}
