package com.duo.core.shiro.realm;

import com.duo.core.utils.ShiroUtils;
import com.duo.modules.system.entity.SystemOnline;
import com.duo.modules.system.mapper.SystemOnlineMapper;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

/**
 * @author [ Yonsin ] on [2019/11/1]
 * @Description： [ 继承EnterpriseCacheSessionDAO 构造方法用ConcurrentHashMap,执行doCreater后执行CachingSessionDAO的create方法将session放缓存、 * CachingSessionDAO自带map缓存，
 * 但在安全管理器中注入了redis，就会将seesion也放到redis
 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class DUOSessionDAO  extends EnterpriseCacheSessionDAO {
    @Autowired
    SystemOnlineMapper systemOnlineMapper;

    /*创建session	Creater session 是第一次请求，如果浏览器没有jseesion就创建并写给浏览器
    如果安全管理器中的seesion失效了，用户登陆是携带jsessionid，先执行readSession，
    通过Jsessionid区找Session,找到了就给安全管理器，找不到就执行creatSession方法重新创建session。
    */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session); //
        //Serializable sessionId = generateSessionId(session);
        //assignSessionId(session, sessionId);
        return sessionId;
    }

    //获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        return session;
    }

    //更新session .shiro对每一次请求都会更新最后访问时间.当一个页面包含多个资源的时候就会发生多次update session
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);

    }

    //删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        SystemOnline userSession= systemOnlineMapper.selectByPrimaryKey((String)session.getId());
        userSession.setLogin_status("0");
        userSession.setLogout_time(new Date());
        systemOnlineMapper.updateByPrimaryKey(userSession);
    }
}
