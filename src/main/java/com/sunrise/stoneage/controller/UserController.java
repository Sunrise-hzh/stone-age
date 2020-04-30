package com.sunrise.stoneage.controller;

import com.sunrise.stoneage.common.MyResult;
import com.sunrise.stoneage.dto.UserDTO;
import com.sunrise.stoneage.mbg.model.UserDO;
import com.sunrise.stoneage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public MyResult<List<UserDO>> getAll(){
        return MyResult.success(userService.getAll());
    }

    @PostMapping
    public MyResult<String> add(@RequestBody UserDTO dto){
        userService.add(dto);
        return MyResult.success("添加成功");
    }
}
