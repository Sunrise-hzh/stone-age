package com.sunrise.stoneage.utils;

import com.sunrise.stoneage.mbg.model.UserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;

/**
 * shiro 工具类
 */
public class ShiroUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroUtil.class);

    private ShiroUtil(){}

    private static RedisSessionDAO redisSessionDAO = SpringUtil.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 登出
     */
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static UserDO getUserInfo(){
        return (UserDO) SecurityUtils.getSubject().getPrincipal();
    }

    public static void deleteCache(String username, boolean isRemoveSession){
        // 从缓存中获取session
        Session session = null;
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        UserDO userDO;
        Object attribute = null;

        for (Session sessionInfo : sessions) {
            // 遍历Session， 找到该用户名称对应的session
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            LOGGER.debug("DefaultSubjectContext.PRINCIPALS_SESSION_KEY : {}", DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            LOGGER.debug("attribute : {}", attribute);
            if (attribute == null){
                continue;
            }
            userDO = (UserDO) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (userDO == null) {
                continue;
            }
            if (Objects.equals(userDO.getUsername(), username)){
                session = sessionInfo;
                break;
            }
        }
        if (session == null || attribute == null) {
            return;
        }
        // 删除session
        if (isRemoveSession) {
            redisSessionDAO.delete(session);
        }
        // 删除cache，在访问受限接口时会重新授权
        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authenticator = securityManager.getAuthenticator();
        ((LogoutAware) authenticator).onLogout((SimplePrincipalCollection) attribute);
    }
}
