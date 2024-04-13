package com.scnu.travel.mapper;

import com.scnu.travel.pojo.Permission;
import com.scnu.travel.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PermissionMapperTest {
    @Autowired
    private PermissionService permissionService;

    @Test
    public void testFindPermission(){
        Permission permission = permissionService.findPermission(1);
        System.out.println(permission);
    }

    @Test
    public void testDeletePermission(){
        permissionService.deletePermission(5);
    }
}
