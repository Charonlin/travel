package com.scnu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.bean.RoleWithStatus;
import com.scnu.travel.mapper.AdminMapper;
import com.scnu.travel.mapper.RoleMapper;
import com.scnu.travel.pojo.Admin;
import com.scnu.travel.pojo.Permission;
import com.scnu.travel.pojo.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.perc.PercInstantiator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;


    //分页查询管理员
    public Page<Admin> findPage(int page,int size){
        Page selectPage = adminMapper.selectPage(new Page(page, size), null);
        return selectPage;
    }

    //新增管理员
    public void add(Admin admin){
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminMapper.insert(admin);
    }

    //根据id查询管理员
    public Admin findById(Integer aid){
        return adminMapper.selectById(aid);
    }

    //更新管理员信息
    public void update(Admin admin){
        //1.先拿到旧密码
        String oldPassword = adminMapper.selectById(admin.getAid()).getPassword();
        //2.后拿到新密码
        String newPassword = admin.getPassword();
        if (!oldPassword.equals(newPassword)){
            admin.setPassword(encoder.encode(admin.getPassword()));
        }
        adminMapper.updateById(admin);
    }

    //根据id查询管理员详情
    public Admin findDesc(Integer aid){
        Admin desc = adminMapper.findDesc(aid);
        return desc;
    }

    //查询用户的角色情况
    public List<RoleWithStatus> findRole(Integer aid){
        //1.查询所有角色
        List<Role> roles = roleMapper.selectList(null);
        //2.查询用户所拥有的角色
        List<Integer> rids = roleMapper.findRoleIdByAdmin(aid);
        //3.构建带有状态的角色集合，拥有状态为true，否则则为false
        List<RoleWithStatus> roleList = new ArrayList<>();
        for(Role role:roles){
            //创建带有状态的角色
            RoleWithStatus roleWithStatus = new RoleWithStatus();
            //复制对象的属性
            BeanUtils.copyProperties(role,roleWithStatus);
            if (rids.contains(role.getRid())){//如果用户拥有该角色
                roleWithStatus.setAdminHas(true);
            }else{
                roleWithStatus.setAdminHas(false);
            }
            roleList.add(roleWithStatus);
        }
        return roleList;
    }

    //修改用户的角色
    public void updateAdminRole(Integer aid,Integer[] rids){
        adminMapper.deleteAdminAllRoles(aid);
        for (Integer rid:rids){
            adminMapper.addAdminRole(aid,rid);
        }
    }

    //修改用户状态
    public void updateStatus(Integer aid){
        Admin admin = adminMapper.selectById(aid);
        admin.setStatus(!admin.isStatus());//状态转向
        adminMapper.updateById(admin);
    }

    public Admin findByAdminName(String username){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Admin admin = adminMapper.selectOne(wrapper);
        return admin;
    }

    //根据名字查明权限
    public List<Permission> finAllPermissionByAdminName(String username){
        List<Permission> permissions = adminMapper.finAllPermission(username);
        return permissions;
    }
}
