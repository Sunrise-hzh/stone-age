package com.sunrise.stoneage.config.exception;

import com.sunrise.stoneage.common.MyResult;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author huangzihua
 * @date 2020-04-24
 */
//@Order(100)
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * shiro权限验证异常
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public MyResult authorizationExceptionHandler(){
        return MyResult.forbidden();
    }

    /**
     * shiro身份验证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public MyResult authenticationExceptionHandler(AuthenticationException exception){
        if (exception instanceof UnknownAccountException || exception instanceof IncorrectCredentialsException){
            return MyResult.failure("用户名或密码错误！");
        } else if (exception instanceof LockedAccountException){
            return MyResult.failure("该账号已被锁定！");
        } else if (exception instanceof DisabledAccountException) {
            return MyResult.failure("该账号已被禁用！");
        } else {
            return MyResult.failure("登录失败！");
        }
    }


    /**
     * 全局异常
     */
    @ExceptionHandler(value = Exception.class)
    public MyResult exception(Exception exception){
        // 打印日志...
        LOGGER.info("异常：{}",exception);
        return MyResult.failure(exception.getMessage());
    }
}
