package com.sunrise.stoneage.dao;

import com.sunrise.stoneage.mbg.mapper.UserBaseMapper;
import com.sunrise.stoneage.mbg.model.UserDO;

public interface UserMapper extends UserBaseMapper {

    UserDO getUserByUsername(String username);
}
