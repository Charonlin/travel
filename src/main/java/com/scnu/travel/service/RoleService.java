package com.scnu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.bean.PermissionWithStatus;
import com.scnu.travel.mapper.PermissionMapper;
import com.scnu.travel.mapper.RoleMapper;
import com.scnu.travel.pojo.Permission;
import com.scnu.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    //分页查询的过程
    public Page<Role> findPage(int page, int size){
        Page roles = roleMapper.selectPage(new Page(page, size), null);
        return roles;
    }

    //添加角色种类
    public void addRole(Role role){
        roleMapper.insert(role);
    }

    //根据角色rid获取角色
    public Role findRole(Integer rid){
        return roleMapper.selectById(rid);
    }

    //保存角色信息
    public void update(Role role){
        roleMapper.updateById(role);
    }

    //获取角色详情信息
    public Role findDesc(Integer rid){
        Role role = roleMapper.findDesc(rid);
        return role;
    }

    //查询角色的权限情况
    public List<PermissionWithStatus> findPermission(Integer rid){
        //1.获取角色的所有权限id
        List<Integer> pids = permissionMapper.findPermissionIdByRole(rid);
        //2.查询所有的权限id
        List<Permission> permissions = permissionMapper.selectList(null);
        //3.建立带有状态的权限集合
        List<PermissionWithStatus> permissionList = new ArrayList<>();
        for (Permission permission:permissions){
            //4.创建带有状态的权限
            PermissionWithStatus permissionWithStatus = new PermissionWithStatus();
            BeanUtils.copyProperties(permission,permissionWithStatus);
            //5.增加判定条件，如果角色拥有该权限，则修改
            if (pids.contains(permission.getPid())){
                permissionWithStatus.setRolesHas(true);
            }else{
                permissionWithStatus.setRolesHas(false);
            }
            permissionList.add(permissionWithStatus);
        }
        return permissionList;
    }

    //更新角色的新权限
    public void updateRolePermission(Integer rid, Integer[] ids){
        //1.先删除角色的所有权限
        roleMapper.deleteRoleAllPermission(rid);
        System.out.println(ids);
        //2.添加上用户新的权限
        for(Integer pid:ids){
            roleMapper.addRolePermission(rid,pid);
        }
    }

    //删除角色
    public void deleteRoleByRid(Integer rid){
        roleMapper.deleteById(rid);
    }
}
