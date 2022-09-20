package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.Admin;
import com.guigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;
    //分页及带条件的查询
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放到request域中
        map.put("filters",filters);
        //调用AdminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        //将PageInfo对象放到request域中
        map.put("page",pageInfo);
        return "admin/index";
    }

    //去添加用户的页面
    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }
    //保存用户
    @RequestMapping("/save")
    public String save(Admin admin){
        //调用AdminService中保存的方法
        adminService.insert(admin);
        return "common/successPage";
    }
    //删除用户
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId){
        //调用AdminService中删除的方法
        adminService.delete(adminId);
        //重定向
        return "redirect:/admin";
    }
    //去更新的页面
    @RequestMapping("/edit/{adminId}")
    public String goEditPage(@PathVariable("adminId") Long adminId,Map map){
        //调用AdminService中根据Id查询一个对象的方法
        Admin admin = adminService.getById(adminId);
        //将admin对象放到request域中
        map.put("admin",admin);
        return "admin/edit";
    }
    //更新用户
    @RequestMapping("update")
    public String update(Admin admin){
        //调用AdminService中更新的方法
        adminService.update(admin);
        return "common/successPage";
    }
}
