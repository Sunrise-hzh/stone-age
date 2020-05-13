package com.sunrise.stoneage.config.shiro;

import com.sunrise.stoneage.utils.EncryptionUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private final int EXPIRE = 1800;    // 30分钟

    //Redis配置
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 开启Shiro-aop注解支持
     * @Attention 使用代理方式所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro基础配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 注意过滤器配置顺序不能颠倒
        // 配置过滤:不会被拦截的链接
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/account/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器。
     * 即所有与安全有关的操作都会与 SecurityManager 交互；且其管理着所有 Subject
     * 可以看出它是 Shiro 的核心，它负责与 Shiro 的其他组件进行交互，它相当于 SpringMVC 中 DispatcherServlet 的角色。
     * 与Subject的所有交互都会委托给SecurityManager，Subject只是一个门面，实际执行者是SecurityManager。
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置Session管理器。
        // DefaultWebSecurityManager的默认SessionManager会话管理器是ServletContainerSessionManager。
        securityManager.setSessionManager(sessionManager());

        // 配置Cache管理器
        securityManager.setCacheManager(redisCacheManager());

        // 自定义Realm验证
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    /**
     * 身份验证器
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用SHA256算法;
        shaCredentialsMatcher.setHashAlgorithmName(EncryptionUtil.SHA_256);
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        shaCredentialsMatcher.setHashIterations(EncryptionUtil.HASH_ITERATIONS);
        return shaCredentialsMatcher;
    }



    /**
     * 配置Cache管理器
     * 用于往Redis存储权限和角色标识
     * @Attention 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        // 配置缓存的话要求放在session里面的实体类必须有个id标识
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;

    }




    /**
     * 配置Session管理器
     */
//    @Bean
//    public SessionManager sessionManager(){
//        /*
//            在Web环境中，Shiro的默认会话管理器SessionManager实现是ServletContainerSessionManager。
//              该实现将所有会话管理任务（包括Servlet容器支持的会话集群）委托给运行时的Servlet容器。缺点是不可移植。
//              详情参考Shiro官方文档：http://shiro.apache.org/web.html#native-sessions
//            解决办法：启用Shiro的本地会话管理。
//              只需在SecurityManager中设置DefaultWebSessionManager实例即可。
//              DefaultWebSessionManager提供超时设置、会话集群等选项，详情参考Shiro官方文档：http://shiro.apache.org/session-management.html
//         */
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(30*60*1000);   // 设置会话超时时间30分钟，单位毫秒
////        sessionManager.setSessionIdCookieEnabled(false);    // 禁用本地会话cookie
//        sessionManager.setSessionDAO(ehcacheSessionDAO());      // 配置SessionDAO。
//        return sessionManager;
//    }

    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(redisSessionDAO());
        return shiroSessionManager;
    }


    /**
     * SessionDAO 的作用是持久化Session会话信息。可保存到内存、文件系统、关系型数据库、NoSQL数据库等。
     * 此处使用Shiro官方推荐的DAO。官方文档：http://shiro.apache.org/session-management.html#session-storage
     * EhcacheSessionDAO：
     * @return
     */
//    @Bean
//    public SessionDAO ehcacheSessionDAO(){
//        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
//        return sessionDAO;
//    }

//    @Bean
//    public CacheManager ehcacheManager(){
//        CacheManager cacheManager = new EhCacheManager();
//        return cacheManager;
//    }


    /**
     * 配置RedisSessionDAO
     * @Attention 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setExpire(EXPIRE);  // 设置到期时间，秒
        return redisSessionDAO;
    }

    /**
     * 配置Redis管理器
     * @Attention 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * SessionID生成器
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator(){
        return new ShiroSessionIdGenerator();
    }
}
