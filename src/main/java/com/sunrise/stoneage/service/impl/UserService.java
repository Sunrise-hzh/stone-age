package com.sunrise.stoneage.service.impl;

import com.sunrise.stoneage.dao.UserMapper;
import com.sunrise.stoneage.dto.UserDTO;
import com.sunrise.stoneage.mbg.model.UserDO;
import com.sunrise.stoneage.service.IUserService;
import com.sunrise.stoneage.utils.EncryptionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    @Override
    public int add(UserDTO userDTO) {
        if (userMapper.countExistByUsername(userDTO.getUsername()) > 0){
            throw new RuntimeException("该用户名已存在!");
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userDO.setSalt(UUID.randomUUID().toString().substring(0, 10));
        userDO.setPassword(EncryptionUtil.sha256(userDTO.getPassword(), userDO.getSalt()));
        userDO.setIsDeleted(false);
        userDO.setIsEnable(true);
        return userMapper.insert(userDO);
    }
}
