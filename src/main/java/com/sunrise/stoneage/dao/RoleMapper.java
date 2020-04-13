package com.sunrise.stoneage.dao;

import com.sunrise.stoneage.mbg.mapper.RoleBaseMapper;
import com.sunrise.stoneage.mbg.model.RoleDO;

import java.util.List;

public interface RoleMapper extends RoleBaseMapper {
    List<RoleDO> getRolesByUserId(Long userId);
}
