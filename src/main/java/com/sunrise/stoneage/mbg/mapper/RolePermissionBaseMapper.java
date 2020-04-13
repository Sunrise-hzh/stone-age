package com.sunrise.stoneage.mbg.mapper;

import com.sunrise.stoneage.mbg.model.RolePermissionDO;
import java.util.List;

public interface RolePermissionBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermissionDO record);

    RolePermissionDO selectByPrimaryKey(Long id);

    List<RolePermissionDO> selectAll();

    int updateByPrimaryKey(RolePermissionDO record);
}