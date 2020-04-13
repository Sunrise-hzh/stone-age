package com.sunrise.stoneage.mbg.mapper;

import com.sunrise.stoneage.mbg.model.PermissionDO;
import java.util.List;

public interface PermissionBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionDO record);

    PermissionDO selectByPrimaryKey(Long id);

    List<PermissionDO> selectAll();

    int updateByPrimaryKey(PermissionDO record);
}