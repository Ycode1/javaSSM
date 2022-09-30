package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.Admin;
import com.guigu.service.AdminService;
import com.guigu.service.RoleService;
import com.guigu.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService ;
    //注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder ;
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
        //对Admin对象中的密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
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
    //去上传头像的页面
    @RequestMapping("uploadShow/{id}")
    public String goUploadPage(@PathVariable("id") Long id,Map map){
        //放入request域中
        map.put("id",id);
        return "admin/upload";
    }
    //上传头像
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id") Long id, MultipartFile file){
        try {
            //根据AdminService中genuineid查询用户的方法
            Admin admin = adminService.getById(id);
            //获取文件的名字
            byte[] bytes = file.getBytes();
            //根据UUID随机生成一个文件名
            String fileName = UUID.randomUUID().toString();
            //通过工具类上传到七牛云
            QiniuUtils.upload2Qiniu(bytes,fileName);
            //给用户设置头像地址
            admin.setHeadUrl("http://ripfkt1nq.hn-bkt.clouddn.com/"+fileName);
            //调用AdminService中更新的方法
             adminService.update(admin);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "common/successPage";
    }
    //去分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId") Long adminId, ModelMap modelMap){
        //将用户的id放到request域中
        modelMap.addAttribute("adminId",adminId);
        //调用RoleService中的方法
        Map<String,Object> rolesByAdminId = roleService.findRolesByAdminId(adminId);
        //将map放入request域中
        modelMap.addAllAttributes(rolesByAdminId);
        return "admin/assignShow";
    }
    //分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId , Long[] roleIds){
        //调用RoleService中分配角色中的方法
        roleService.assignRole(adminId,roleIds);
        return "common/successPage";
    }

}
