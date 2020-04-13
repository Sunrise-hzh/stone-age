package com.sunrise.stoneage.service;

import com.sunrise.stoneage.mbg.model.PermissionDO;

import java.util.List;

public interface IPermissionService {
    List<PermissionDO> getPermissionsByRoleId(Long roleId);
}
