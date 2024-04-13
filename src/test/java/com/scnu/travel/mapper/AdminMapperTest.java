package com.scnu.travel.mapper;

import com.scnu.travel.pojo.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testFindDest(){
        Admin admin = adminMapper.findDesc(1);
        System.out.println(admin);
    }

    @Test
    public void testFindRole(){

    }

}
