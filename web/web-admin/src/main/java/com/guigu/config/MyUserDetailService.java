package com.guigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.guigu.entity.Admin;
import com.guigu.service.AdminService;
import com.guigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Component
public class MyUserDetailService implements UserDetailsService {
    //这里可以把这个MyUserDetailService当作Controller用
    @Reference
    private AdminService adminService ;
    @Reference
    private PermissionService permissionService ;
    //用户登录时SpringSecurity调用该方法
    // 并将用户名传入到该方法中 用户名以参数名为默认密码
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用AdminService中根据用户名查询Admin对象的方法
        Admin admin = adminService.getAdminByUserName(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //调用PermissionService中获取当前用户权限码的方法
       List<String> permissionCodes =  permissionService.getPermissionCodesByAdminId(admin.getId());
        //创建一个用于授权的集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        //遍历得到每一个权限码
        for (String permissionCode : permissionCodes) {
            if(StringUtils.isEmpty(permissionCode)) continue;
            //创建GrantedAuthority对象
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionCode);
            //将simpleGrantedAuthority对象放入权限集合中
            authorities.add(simpleGrantedAuthority);
        }
        /*
            给用户授权
                权限有两种标识方式
                    1、通过角色的方式表示 例如ROLE.ADMIN
                    2、直接设置权限 表中的admin.edit啥的 在permission里面的code
         */
        return  new User(username,admin.getPassword(), authorities);
    }






}
