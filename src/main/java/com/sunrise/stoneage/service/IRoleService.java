package com.sunrise.stoneage.service;

import com.sunrise.stoneage.mbg.model.RoleDO;

import java.util.List;

public interface IRoleService {
    List<RoleDO> getRolesByUserId(Long userId);
}
