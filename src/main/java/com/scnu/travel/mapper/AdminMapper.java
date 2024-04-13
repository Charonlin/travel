package com.scnu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scnu.travel.pojo.Admin;
import com.scnu.travel.pojo.Permission;
import jdk.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    //查询用户详情
    Admin findDesc(Integer aid);

    //删除用户的所有角色

    void deleteAdminAllRoles(Integer aid);

    //添加用户的所有角色
    void addAdminRole(@Param("aid") Integer aid, @Param("rid") Integer rid);

    //根据用户名查询权限
    List<Permission> finAllPermission(String username);
}
