package com.guigu.service;

import com.guigu.entity.Admin;

import java.util.List;

public interface AdminService extends  BaseService<Admin> {

    List<Admin> findAll();

    Admin getAdminByUserName(String username);
}
