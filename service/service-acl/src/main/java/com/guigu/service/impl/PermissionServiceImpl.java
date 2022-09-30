package com.guigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.guigu.dao.BaseDao;
import com.guigu.dao.PermissionDao;
import com.guigu.dao.RolePermissionDao;
import com.guigu.entity.Permission;
import com.guigu.helper.PermissionHelper;
import com.guigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao  permissionDao ;
    @Autowired
    private RolePermissionDao rolePermissionDao ;
    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRole(Long roleId) {
       //获取所有的权限
        List<Permission> permissionList = permissionDao.findAll();
        //根据角色id查询已分配的权限的Id
       List<Long>permissionIds =  rolePermissionDao.findPermissionIdsByRole(roleId);
       //创建返回的List
        List<Map<String,Object>> returnList = new ArrayList<>();


        for (Permission permission : permissionList) {

            // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
            Map<String,Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            //判断当前权限的Id在不在permissionIds
            if(permissionIds.contains(permission.getId())){
                //证明该权限是已经分配的权限
                map.put("checked", true);
            }
                returnList.add(map);

        }
        return returnList ;
    }

    @Override
    public void assignPermisssion(Long roleId, Long[] permissionIds) {
        //调用RolePermissionDao中根据角色Id删除已分配权限的方法
        rolePermissionDao.deletePermissionIdsByRoleId(roleId);
        //遍历所有的权限id
        for (Long permissionId : permissionIds) {
            if(permissionId != null){
                //调用RolePermissionDao中保存权限id和角色id的方法
                rolePermissionDao.addRoleAndPermission(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList = null ;
       //判断是否是超级管理员
        if(userId == 1){
            //证明是系统管理员 获取所有的权限菜单
            permissionList = permissionDao.findAll();
        }else{
            //根据用户的id查询权限菜单
            permissionList = permissionDao.getMenuPermissionByAdminId(userId);
        }
        //通过PermissionHelper工具类和List转换成树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);

        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null ;
        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }
}
