package com.guigu.service;

import com.guigu.entity.Role;

import java.util.List;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

}
