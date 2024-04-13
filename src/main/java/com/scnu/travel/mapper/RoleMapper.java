package com.scnu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scnu.travel.pojo.Admin;
import com.scnu.travel.pojo.Permission;
import com.scnu.travel.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    //查所有询用户拥有的角色的id
    List<Integer> findRoleIdByAdmin(Integer aid);

    Role findDesc(Integer rid);

    //删除角色本身具有的权限
    void deleteRoleAllPermission(Integer rid);

    //增加角色新的权限
    void addRolePermission(@Param("rid") Integer rid,@Param("pid") Integer pid);
}
