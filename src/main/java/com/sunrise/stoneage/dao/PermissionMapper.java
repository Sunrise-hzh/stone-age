package com.sunrise.stoneage.dao;

import com.sunrise.stoneage.mbg.mapper.PermissionBaseMapper;
import com.sunrise.stoneage.mbg.model.PermissionDO;

import java.util.List;

public interface PermissionMapper extends PermissionBaseMapper{
    List<PermissionDO> getPermissionsByRoleId(Long roleId);
}
