package com.guigu.service;

import com.guigu.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends  BaseService<Permission> {

    List<Map<String, Object>> findPermissionByRole(Long roleId);

    void assignPermisssion(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByAdminId(Long userId);

    List<Permission> findAllMenu();

    List<String> getPermissionCodesByAdminId(Long id);
}
