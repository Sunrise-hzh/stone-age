package com.sunrise.stoneage.service.impl;

import com.sunrise.stoneage.dao.PermissionMapper;
import com.sunrise.stoneage.mbg.model.PermissionDO;
import com.sunrise.stoneage.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("permissionService")
public class PermissionService implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDO> getPermissionsByRoleId(Long roleId) {
        return permissionMapper.getPermissionsByRoleId(roleId);
    }
}
