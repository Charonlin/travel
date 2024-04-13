package com.scnu.travel.mapper;

import com.scnu.travel.pojo.Role;
import com.scnu.travel.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoleMapperTest {
    @Autowired
    public RoleService roleService;

    @Test
    public void testFindRoleById(){
        Role role = roleService.findRole(1);
        System.out.println(role);
    }

    @Test
    public void testFindDesc(){
        Role role = roleService.findDesc(1);
        System.out.println(role);
    }

//    @Test
//    public void testFindPermissionByRoleId(){
//        List<Integer> pids = roleService.findPermissionByRoleId(1);
//        pids.forEach(System.out::println);
//    }

//    @Test
//    public void testUpdateRolePermission(){
//        ;
//    }
}
