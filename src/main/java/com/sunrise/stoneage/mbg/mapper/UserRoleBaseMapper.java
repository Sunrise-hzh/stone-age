package com.sunrise.stoneage.mbg.mapper;

import com.sunrise.stoneage.mbg.model.UserRoleDO;
import java.util.List;

public interface UserRoleBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleDO record);

    UserRoleDO selectByPrimaryKey(Long id);

    List<UserRoleDO> selectAll();

    int updateByPrimaryKey(UserRoleDO record);
}