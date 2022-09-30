package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.AdminDao;
import com.guigu.dao.AdminRoleDao;
import com.guigu.dao.BaseDao;
import com.guigu.dao.RoleDao;
import com.guigu.entity.AdminRole;
import com.guigu.entity.Role;
import com.guigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AdminRoleDao adminRoleDao ;
    @Override
    public List<Role> findAll() {
        return  roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        //获取所有的角色
        List<Role> roleList = roleDao.findAll() ;
        //获取用户已拥有的角色的角色id
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);
        //对角色进行分类 创建两个list
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        //遍历所有的角色
        for (Role role : roleList) {
            //判断当前角色的id在不在集合roleIds中
            if(roleIds.contains(role.getId())){
                //将当前角色放入已选中的角色中
                assginRoleList.add(role);
            }else{
                //证明当前角色是未选中的角色，
                noAssginRoleList.add(role);
            }
        }
        //创建返回的map
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList", noAssginRoleList);
        roleMap.put("assginRoleList", assginRoleList);
        return  roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        //先根据用户的id将已分配的角色删除
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        //遍历所有的角色id
        for (Long roleId : roleIds) {
             if(roleId != null){
                 //将角色id和用户id插入到数据库中
                 adminRoleDao.addRoleIdAndAdminId(roleId,adminId);
             }

        }
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }



}
