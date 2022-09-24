package com.guigu.dao;

import com.guigu.entity.Admin;

import java.util.List;

public interface AdminDao extends  BaseDao<Admin> {
    List<Admin> findAll();
}
