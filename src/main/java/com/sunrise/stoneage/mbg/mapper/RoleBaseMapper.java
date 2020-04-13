package com.sunrise.stoneage.mbg.mapper;

import com.sunrise.stoneage.mbg.model.RoleDO;
import java.util.List;

public interface RoleBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleDO record);

    RoleDO selectByPrimaryKey(Long id);

    List<RoleDO> selectAll();

    int updateByPrimaryKey(RoleDO record);
}