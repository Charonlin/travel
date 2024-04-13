package com.scnu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scnu.travel.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    //查询角色所拥有的所有权限的id
    List<Integer> findPermissionIdByRole(Integer rid);

}
