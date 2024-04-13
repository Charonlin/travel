package com.scnu.travel.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.pojo.Product;
import com.scnu.travel.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class productMapperTest {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;

    @Test
    public void testFindProductPage(){
        Page<Product> productPage = productMapper.findProductPage(new Page(1, 5));
        System.out.println(productPage);
    }

    @Test
    public void testFindOne(){
        Product one = productService.findOne(1);
        System.out.println(one);
    }
}
