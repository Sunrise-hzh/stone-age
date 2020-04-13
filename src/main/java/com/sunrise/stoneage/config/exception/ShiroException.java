package com.sunrise.stoneage.config.exception;

import com.sunrise.stoneage.common.MyResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShiroException {

    @ExceptionHandler(value = AuthorizationException.class)
    public MyResult defaultErrorHandler(){
        return MyResult.forbidden();
    }
}
