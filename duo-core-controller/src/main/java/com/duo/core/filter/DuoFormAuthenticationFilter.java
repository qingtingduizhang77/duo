package com.duo.core.filter;

import com.duo.Constants;
import com.duo.MemCache;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.service.SystemUserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class DuoFormAuthenticationFilter extends FormAuthenticationFilter {


    private RedisTemplate<String, Object> redisTemplate;
    public DuoFormAuthenticationFilter(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // 在这里进行验证码的校验
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(httpServletRequest);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Login Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
//        System.out.println("ProjectID = " + ShiroUtils.getSessionAttribute("ProjectID") + ", SystemID = " + ShiroUtils.getSessionAttribute("SystemID"));

        // 取出验证码
        String validateCode = (String) session.getAttribute("Login:ImageCode:"+session.getId());
        String cacheType=  MemCache.getSystemParameter("cache-type");
        if(cacheType!=null&&cacheType.equals("redis")){
                validateCode= String.valueOf(redisTemplate.opsForValue().get("Login:ImageCode:"+session.getId()));
        }
        // 取出页面的验证码
        // 输入的验证和session中的验证进行对比
        if(StringUtils.isNotEmpty(validateCode)) {
            String randomcode = httpServletRequest.getParameter("validatecode");
            if (randomcode != null && !randomcode.equals(validateCode)) {
                // 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
                httpServletRequest.setAttribute("shiroLoginFailure", "kaptchaValidateFailed");//自定义登录异常
                // 拒绝访问，不再校验账号和密码
                return true;
            }
        }
        String isSaaS=  MemCache.getSystemParameter("isSaaS");
        if(requestMap.containsKey("ProjectID")){
            ShiroUtils.setSessionAttribute("ProjectID",requestMap.get("ProjectID"));
        }else{//SaaS系统必须要传入ProjcetID，单应用项目会找到默认第一个项目
            if(isSaaS!=null&&isSaaS.equals("true")) {
                httpServletRequest.setAttribute("shiroLoginFailure", "projectValidateFailed");//自定义登录异常
                return true;
            }
        }

        if(requestMap.containsKey("SystemID")){

            ShiroUtils.setSessionAttribute("SystemID",requestMap.get("SystemID"));
        }else{//SaaS系统必须要传入SystemID，单应用项目会找到默认系统
            if(isSaaS!=null&&isSaaS.equals("true")) {
                httpServletRequest.setAttribute("shiroLoginFailure", "systemValidateFailed");//自定义登录异常
                return true;
            }
        }
        return super.onAccessDenied(request, response);
    }



    /**
     * 思路：用户输入凭证之后就会交给FormAuthenticationFilter这个类来处理，如果登录成功之后就会调用onLoginSuccess()方法重定向,
     * 最后调用WebUtils.redirectToSavedRequest()方法，该方法中可以看出shiro去session中找出之前的保存的请求，
     * 如果没有的话就会跳转到我们配置的successUrl。
     * 解决办法：用户回话过期后，重新登录后会访问session保存的访问地址造成页面布局混乱，这里直接清除之前保存的session中保存的请求信息，并且清理它
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
//        SavedRequest savedRequest= WebUtils.getAndClearSavedRequest(request);//清除之前的地址（跳转到登录页面之前要访问的地址）
//        if(savedRequest!=null) {
//            String successUrl = savedRequest.getRequestUrl();
//            System.out.println("ppppppppppppppppppppppp" + successUrl);
//            if (successUrl.indexOf("login") > -1) successUrl = "/";
//            successUrl = StringEscapeUtils.unescapeHtml(successUrl);
//            System.out.println("ppppppppppppppppppppppp" + successUrl);
//
//            WebUtils.redirectToSavedRequest(request, response, successUrl);
//        }
//        return false;
    }
}