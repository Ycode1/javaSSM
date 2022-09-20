package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.AdminDao;
import com.guigu.dao.BaseDao;
import com.guigu.entity.Admin;
import com.guigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends  BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }
}
