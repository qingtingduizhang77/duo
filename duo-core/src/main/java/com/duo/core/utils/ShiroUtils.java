package com.duo.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.duo.MemCache;
import com.duo.core.BaseEntity;
import com.duo.core.exception.DUOException;
import com.duo.modules.common.service.SystemUserService;
import com.duo.modules.system.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Shiro工具类
 * 
 * @author yonsin
 * @date 2019年11月12日 上午9:49:19
 */
@Slf4j
@Component
public class ShiroUtils {

	@Autowired
	private  SystemUserService systemUserService;
	private static ShiroUtils shiroUtils;

	@PostConstruct
	public void init() {
		shiroUtils = this;

	}

	public static boolean isLogin() {
		try {
			return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null;
		}catch (Exception e){

		}
		return false;
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}
	public static String getSessionId() {
		if(isLogin())
		return String.valueOf(SecurityUtils.getSubject().getSession().getId());
		return null;
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static String getUserCode() {
	//	log.info("Current login User:"+(String)SecurityUtils.getSubject().getPrincipal());
		return (String)SecurityUtils.getSubject().getPrincipal();
	}
	//用户对象
	public static BaseEntity getUser() {
		if(isLogin()) {
			if(MemCache._userInfo.containsKey(getUserCode())) return MemCache._userInfo.get(getUserCode());
			else {
				MemCache._userInfo.put(getUserCode(),shiroUtils.systemUserService.findByUsername(getUserCode()));
				return MemCache._userInfo.get(getUserCode());
			}
		}
		else return null;
	}
	//用户id
	public static String getUserId() {
		if(isLogin())
		return ((SystemUser)getUser()).getUser_id();
		else return "";
	}
	//用户名称
	public static String getUserName() {
		if(isLogin())
		return ((SystemUser)getUser()).getUser_name();
		else return "";
	}
	//部门id
	public static String getDeptId() {
		if(isLogin())
			return ((SystemUser)getUser()).getDept_id();
		else return "";
	}
	//公司id
	public static String getCompanyId() {
		if(isLogin()) {
			int level=Integer.parseInt(MemCache.getSystemParameter("companyLevel"));
			String id=getDeptId();
			return getDeptId().substring(0,4*level);
		}else return "";
	}
	//一级部门id
	public static String getOneDeptId() {
		if(isLogin()) {
			int level=Integer.parseInt(MemCache.getSystemParameter("companyLevel"))+1;
			String id=getDeptId();
			if(4*level>id.length()){
				return id;
			}else {
				return getDeptId().substring(0, 4 * level);
			}
		}else return "";
	}
	//二级部门id
	public static String getTwoDeptId() {
		if(isLogin()) {
			int level=Integer.parseInt(MemCache.getSystemParameter("companyLevel"))+2;
			String id=getDeptId();
			if(4*level>id.length()){
				return id;
			}else {
				return getDeptId().substring(0, 4 * level);
			}
		}else return "";
	}
	//部门编码
	public static String getDeptCode() {
		if(isLogin())
			return ((SystemUser)getUser()).getDept_code();
		else return "";
	}
	//部门名称
	public static String getDeptName() {
		if(isLogin())
			return ((SystemUser)getUser()).getDept_name();
		else return "";
	}
	//Project_id
	public static String getProjectId() {
		if(isLogin())
			return (String)ShiroUtils.getSessionAttribute("ProjectID");
		else return "";
	}
	//System_id
	public static String getSystemId() {
		if(isLogin())
			return (String)ShiroUtils.getSessionAttribute("SystemID");
		else return "";
	}
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}


	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new DUOException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}



}
