package com.guigu.dao;

import com.guigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> findRoleIdsByAdminId(Long adminId);

    void deleteRoleIdsByAdminId(Long adminId);

    void addRoleIdAndAdminId(@Param("roleId")Long roleId,@Param("adminId") Long adminId);
}
