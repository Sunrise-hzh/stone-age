package com.sunrise.stoneage.service;

import com.sunrise.stoneage.dto.UserDTO;
import com.sunrise.stoneage.mbg.model.UserDO;

import java.util.List;

public interface IUserService {
    UserDO getUserByUsername(String username);

    List<UserDO> getAll();

    int add(UserDTO userDTO);
}
