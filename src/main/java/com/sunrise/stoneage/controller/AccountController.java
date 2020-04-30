package com.sunrise.stoneage.controller;

import com.sunrise.stoneage.common.MyResult;
import com.sunrise.stoneage.common.ResultCodeEnum;
import com.sunrise.stoneage.query.AccountQuery;
import com.sunrise.stoneage.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangzihua
 * @date 2020-04-23
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @PostMapping("/login")
    public MyResult<String> login(@RequestBody AccountQuery accountQuery){
        accountService.login(accountQuery.getUsername(), accountQuery.getPassword());
        return new MyResult<String>(ResultCodeEnum.SUCCESS, "登录成功");
    }

    @GetMapping("/logout")
    public MyResult<String> logout(){
        accountService.logout();
        return new MyResult<String>(ResultCodeEnum.SUCCESS, "注销成功");
    }
}
