package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Admin;
import com.guigu.entity.Permission;
import com.guigu.service.AdminService;
import com.guigu.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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
     //   Long userId = 1L;
        //调用AdminService中查询的方法
    //    Admin admin = adminService.getById(userId);
        //通过SpringSecurity获取User对象  得到用户授权加上用户对象
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //调用AdminService中根据用户名获取Admin对象的方法
        Admin admin = adminService.getAdminByUserName(user.getUsername());
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
    //去登录页面
    @RequestMapping("/login")
    public String login(){
        return "frame/login.html";
    }
//    //登出
//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
//        //将session失效
//        session.invalidate();
//        //重定向登录的请求
//        return "redirect:/login";
  //  }
    //去没有权限的提示页面
        @RequestMapping("/auth")
        public String auth() {
            return  "frame/auth";
        }
}
