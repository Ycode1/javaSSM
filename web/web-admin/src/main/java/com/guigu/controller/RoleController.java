package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.guigu.entity.Role;
import com.guigu.service.PermissionService;
import com.guigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
    public static final String SUCCESS_PAGE="common/successPage";
    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService ;


//    @RequestMapping
//    public String index(Map map ){
//        //调用RoleService中获取所有的角色的办法
//        List<Role> all = roleService.findAll();
//        //将所有的角色放到request域中
//        map.put("list",all);
//        return "role/index";
//    }
    //分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        //获取请求参数
        Map<String, Object> filters = getFilters(request);
        //将filters放到request域中
        map.put("filters",filters);
        //调用RoleService中分页及带条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        //将pageInfo对象放到request域中
        map.put("page",pageInfo);
            return "role/index";
//        model.addAttribute("page", page);
//        model.addAttribute("filters", filters);
//        return PAGE_INDEX;
    }



    //去添加角色的页面
    @RequestMapping("/create")
    public String goAddPage(){
        return "role/create";
    }
    //添加角色
    @RequestMapping("/save")
    public String save(Role role){
        //调用RoleService中添加的方法
        roleService.insert(role);
        //重定向到查询所有的角色的方法
       // return "redirect:/role";
        //去commnon下的成功页面
        return SUCCESS_PAGE;
    }
    //删除角色
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        //调用RoleServce中的删除方法
        roleService.delete(roleId);
        //重定向
        return "redirect:/role";
    }
    //去修改页面的方法
    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable("roleId") Long roleId,Map map){
        //调用roleService中根据id查询的方法
        Role role = roleService.getById(roleId);
        //将查询到的Role对象放到request域中
        map.put("role",role);
        //去修改的页面
        return "role/edit";
    }
    //更新角色
    @RequestMapping("/update")
    public String update(Role role){
        //调用roleService中更新的方法
        roleService.update(role);
        //去common 下 的successPage页面
        return SUCCESS_PAGE;
    }

    //去分配权限的页面
    @RequestMapping("/assignShow/{roleId}")
    public String goAssignShowPage(@PathVariable("roleId") Long roleId ,Map map){
        //将角色id放到request域中
        map.put("roleId",roleId);
        //调用PermissionService中根据角色id 获取权限的方法
        List<Map<String,Object>> zNodes = permissionService.findPermissionByRole(roleId);
            //放入request域中
        map.put("zNodes",zNodes);
        return "role/assignShow" ;
    }
    //分配权限
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId , @RequestParam("permissionIds") Long[] permissionIds){
        //调用PermissionService中分配权限的方法
        permissionService.assignPermisssion(roleId,permissionIds);
        return "common/successPage";
    }

}
