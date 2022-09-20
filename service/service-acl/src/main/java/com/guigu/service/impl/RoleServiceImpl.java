package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.BaseDao;
import com.guigu.dao.RoleDao;
import com.guigu.entity.Role;
import com.guigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return  roleDao.findAll();
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }



}
