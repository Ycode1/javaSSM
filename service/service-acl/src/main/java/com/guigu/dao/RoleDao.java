package com.guigu.dao;

import com.guigu.entity.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role>{
    List<Role> findAll();
}
