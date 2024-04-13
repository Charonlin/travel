package com.scnu.travel.security;

import com.scnu.travel.pojo.Admin;
import com.scnu.travel.pojo.Permission;
import com.scnu.travel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.认证
        Admin admin = adminService.findByAdminName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!admin.isStatus()){
            throw new UsernameNotFoundException("用户不可用");
        }

        //2.授权
        List<Permission> permissions = adminService.finAllPermissionByAdminName(admin.getUsername());
        //将权限转化为Security认识的权限集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission:permissions){
            //因为PermissionDesc的描述就是路径，所以配置成这种形式
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionDesc()));
        }


        //封装为UserDetail对象
        UserDetails userDetails = User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .authorities(grantedAuthorities)
                .build();

        return userDetails;
    }
}
