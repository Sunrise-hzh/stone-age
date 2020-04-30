package com.sunrise.stoneage.service;

/**
 * @author huangzihua
 * @date 2020-04-23
 */
public interface IAccountService {

    /**
     * 登录，即身份认证
     * 流程如下：
     *   1、调用Subject.login(token)进行登录，其自动委托给 SecurityManager；
     *   2、SecurityManager 负责真正的身份验证逻辑，它会委托给 Authenticator 进行身份验证；
     *   3、Authenticator 才是真正的身份验证者，Shiro API 中核心的身份认证入口点，此处可自定义插入自己的实现；
     *   4、Authenticator 可能会委托给相应的 AuthenticationStrategy 进行多 Realm 身份验证，
     *     默认 ModularRealmAuthenticator 会调用 AuthenticationStrategy 进行多 Realm 身份验证；
     *   5、Authenticator 会把相应的 token 传入 Realm，从 Realm 获取身份验证信息，如果没有返回/抛出异常则表示身份验证失败；
     *      此处可配置多个Realm，将按照相应的顺序及策略进行访问。
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password);

    /**
     * 注销
     */
    void logout();
}
