package com.duo.modules.common.web;

import com.duo.core.BaseController;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.VerifyCodeUtil;
import com.wf.captcha.*;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 通用工具类服务 ，无权限约束]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
public class CaptchaController extends BaseController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    RedissonClient redissonClient;
    /**
     * 获取验证码
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/api/getImgCode")
    public void getImgCode(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyCodeUtil.createImage();
        //将验证码存入Session
        log.info("Login:ImageCode:"+session.getId()+"======"+objs[0]);
        session.setAttribute("Login:ImageCode:"+session.getId(),objs[0]);
        //存放到Redis里
        redisUtil.set("Login:ImageCode:"+session.getId(),objs[0],60);//60秒有效
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

/**
 *
 *GifCaptcha Gif验证码
 *SpecCaptcha  png验证码
 * ChineseCaptcha  中文png验证码
 * ChineseGifCaptcha 中文gif验证码
 *
 * 类型	描述
 TYPE_DEFAULT	数字和字母混合
 TYPE_ONLY_NUMBER	纯数字
 TYPE_ONLY_CHAR	纯字母
 TYPE_ONLY_UPPER	纯大写字母
 TYPE_ONLY_LOWER	纯小写字母
 TYPE_NUM_AND_UPPER	数字和大写字母
 */
    @RequestMapping("/api/getCaptcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session=request.getSession();
        String type=request.getParameter("type");
        if(type==null||type.equals(""))type="gif";

        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);

        if(type.equals("png")) {
            // 三个参数分别为宽、高、位数
            SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
            // 设置字体
            specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
            // 设置类型，纯数字、纯字母、字母数字混合
            specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
            // 生成的验证码
            //存放到Redis里
            log.info("Login:ImageCode:"+session.getId()+"======"+specCaptcha.text().toLowerCase());
            //RMapCache<String, String> convertedList = redissonClient.getMapCache("Login:ImageCode");
            //convertedList.fastPut(session.getId(), specCaptcha.text().toLowerCase());
            session.setAttribute("Login:ImageCode:"+session.getId(),specCaptcha.text().toLowerCase());
            redisUtil.set("Login:ImageCode:" + session.getId(), specCaptcha.text().toLowerCase(), 60);//60秒有效
            // 输出图片流
            specCaptcha.out(response.getOutputStream());
        }else if(type.equals("chinesegif")) {
            // 三个参数分别为宽、高、位数
            ChineseGifCaptcha chineseGifCaptcha = new ChineseGifCaptcha(130, 48, 4);
            // 设置字体
            chineseGifCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));  // 有默认字体，可以不用设置
            // 生成的验证码
            //存放到Redis里
            log.info("Login:ImageCode:"+session.getId()+"======"+chineseGifCaptcha.text().toLowerCase());
            session.setAttribute("Login:ImageCode:"+session.getId(),chineseGifCaptcha.text().toLowerCase());
            redisUtil.set("Login:ImageCode:" + session.getId(), chineseGifCaptcha.text().toLowerCase(), 60);//60秒有效
            // 输出图片流
            chineseGifCaptcha.out(response.getOutputStream());
            }else if(type.equals("chinesepng")) {
            // 三个参数分别为宽、高、位数
            ChineseCaptcha chineseCaptcha = new ChineseCaptcha(130, 48, 4);
            // 设置字体
            chineseCaptcha.setFont(new Font("楷体", Font.PLAIN, 28));  // 有默认字体，可以不用设置
            // 生成的验证码
            //存放到Redis里
            log.info("Login:ImageCode:"+session.getId()+"======"+chineseCaptcha.text().toLowerCase());
            session.setAttribute("Login:ImageCode:"+session.getId(),chineseCaptcha.text().toLowerCase());
            redisUtil.set("Login:ImageCode:" + session.getId(), chineseCaptcha.text().toLowerCase(), 60);//60秒有效
            // 输出图片流
            chineseCaptcha.out(response.getOutputStream());
        }else{
            // 三个参数分别为宽、高、位数
            GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
            // 设置字体
            gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
            // 设置类型，纯数字、纯字母、字母数字混合
            gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
            // 验证码存入session
            // request.getSession().setAttribute("captcha", gifCaptcha.text().toLowerCase());
            //存放到Redis里
            log.info("Login:ImageCode:"+session.getId()+"======"+gifCaptcha.text().toLowerCase());
            session.setAttribute("Login:ImageCode:"+session.getId(),gifCaptcha.text().toLowerCase());
            redisUtil.set("Login:ImageCode:" + session.getId(), gifCaptcha.text().toLowerCase(), 60);//60秒有效
            // 输出图片流
            gifCaptcha.out(response.getOutputStream());
        }
    }
}
