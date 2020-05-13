package com.sunrise.stoneage.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * SessionIdGenerator接口：定义SessionID的生成策略，和SessionDAO配合使用。
 *   如果你想自定义SessionID，就必须实现该接口，然后注入到AbstractSessionDAO的实现类中。
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSessionIdGenerator.class);
    @Override
    public Serializable generateId(Session session) {
        // 这里使用shiro内部实现的基于Java JDK UUID的生成器。
        // 因为实现SessionIdGenerator接口，所以必须传入Session对象作为参数，但是该方法并没有用到该参数。
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        LOGGER.info("Session ID is login_token_" + sessionId);
        // 添加前缀 login_token_
        return String.format("login_token_%s", sessionId);
    }
}
