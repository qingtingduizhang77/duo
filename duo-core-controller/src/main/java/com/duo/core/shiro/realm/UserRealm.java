package com.duo.core.shiro.realm;

import com.duo.core.exception.ErrorSystemAccountException;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.service.SystemUserService;
import com.duo.modules.system.entity.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SystemUserService systemUserService;

    //•授权 doGetAuthorizationInfo
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //用户权限
        //authorizationInfo.setRoles(systemUserService.findRoles(username));
       // authorizationInfo.setStringPermissions(systemUserService.findPermissions(username));
        return authorizationInfo;
    }

    //•鉴权 doGetAuthenticationInfo
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        SystemUser user = null;
        if(!StringUtils.isEmpty(username)) {
            user = systemUserService.findByUsername(username);
        }
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        String systemId=(String) ShiroUtils.getSessionAttribute("SystemID");
        if(StringUtils.isNotEmpty(username)&&StringUtils.isNotEmpty(systemId)&&!systemUserService.hasSystemRule(username,systemId)) {
             throw new ErrorSystemAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getIs_valid())) {
            throw new LockedAccountException(); //帐号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUser_code(), //用户名
                user.getUser_password(), //密码
                ByteSource.Util.bytes(user.getUser_code()+user.getUser_salt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }

    /**
     * 认证密码匹配调用方法
     */
    @Override
    protected void assertCredentialsMatch(AuthenticationToken authcToken,
                                          AuthenticationInfo info) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 若单点登录，则使用单点登录授权方法。

        super.assertCredentialsMatch(token, info);
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
