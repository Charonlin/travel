package com.scnu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.mapper.PermissionMapper;
import com.scnu.travel.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    //查询所有的权限，并将其封装为以Permission为属性的页面对象
    public Page<Permission> findPage(int page, int size) {
        Page selectPage = permissionMapper.selectPage(new Page(page, size), null);
        return selectPage;
    }

    //添加单个Permission对象
    public void addPermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    //查询Permission对象
    public Permission findPermission(Integer pid){
        return permissionMapper.selectById(pid);
    }

    //更新Permission对象
    public void updatePermission(Permission permission){
        permissionMapper.updateById(permission);
    }

    //删除Permission对象
    public void deletePermission(Integer pid){
        permissionMapper.deleteById(pid);
    }
}
