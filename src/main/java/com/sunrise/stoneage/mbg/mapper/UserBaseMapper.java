package com.sunrise.stoneage.mbg.mapper;

import com.sunrise.stoneage.mbg.model.UserDO;
import java.util.List;

public interface UserBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    List<UserDO> selectAll();

    int updateByPrimaryKey(UserDO record);
}