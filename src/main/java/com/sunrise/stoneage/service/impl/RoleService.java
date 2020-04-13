package com.sunrise.stoneage.service.impl;

import com.sunrise.stoneage.dao.RoleMapper;
import com.sunrise.stoneage.mbg.model.RoleDO;
import com.sunrise.stoneage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<RoleDO> getRolesByUserId(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
