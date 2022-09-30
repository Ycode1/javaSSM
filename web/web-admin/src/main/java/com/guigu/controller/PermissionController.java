package com.guigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Permission;
import com.guigu.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService ;

    @RequestMapping
    public String index(Map map){
        List<Permission> list = permissionService.findAllMenu();
        map.put("list",list);
        return "permission/index";
    }

    @RequestMapping("/create")
    public String create(Map map , Permission permission){
        map.put("permission",permission);
        return "permission/create";
    }
    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.insert(permission);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String edit(Map map , @PathVariable("id") Long id){
        Permission permissionServiceById = permissionService.getById(id);
        map.put("permissionServiceById",permissionServiceById);
        return "permission/edit";
    }
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        permissionService.delete(id);
        return "redirect:/permission";
    }
    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.update(permission);
        return "common/successPage";
    }

}
