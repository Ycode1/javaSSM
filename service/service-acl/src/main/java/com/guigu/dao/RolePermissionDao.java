package com.guigu.dao;

import com.guigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> findPermissionIdsByRole(Long roleId);

    void deletePermissionIdsByRoleId(Long roleId);

    void addRoleAndPermission(@Param("roleId")Long roleId,@Param("permissionId") Long permissionId);
}
