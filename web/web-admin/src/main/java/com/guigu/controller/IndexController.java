package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Admin;
import com.guigu.entity.Permission;
import com.guigu.service.AdminService;
import com.guigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService ;

    //去首页
//    @RequestMapping("/")
//    public String index(){
//        return "frame/index";
//    }
    @RequestMapping("/")
    public String index(Map map){
        //设置默认的用户id
        Long userId = 1L;
        //调用AdminService中查询的方法
        Admin admin = adminService.getById(userId);
        //根据用户的id调用PermissionService中获取用户权限菜单的方法
        List<Permission> permissionList = permissionService.getMenuPermissionByAdminId(admin.getId());
        //将用户和用户的权限菜单放到request域中
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }
    //去主页面
    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }
}
