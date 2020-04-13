package com.sunrise.stoneage.service.impl;

import com.sunrise.stoneage.dao.UserMapper;
import com.sunrise.stoneage.mbg.model.UserDO;
import com.sunrise.stoneage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public List<UserDO> getAll() {
        return userMapper.selectAll();
    }
}
