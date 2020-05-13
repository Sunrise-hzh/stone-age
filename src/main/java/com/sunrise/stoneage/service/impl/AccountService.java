package com.sunrise.stoneage.service.impl;

import com.sunrise.stoneage.service.IAccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @author huangzihua
 * @date 2020-04-23
 */
@Service("accountService")
public class AccountService implements IAccountService {


    @Override
    public void login(String username, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        // 判断当前用户是否已通过验证。注：isAuthenticated()和 isRemembered() 存在区别。
        if (!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException e) {
                // 打印日志...
                throw e;
            }
        }
    }

    @Override
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}
