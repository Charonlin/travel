package com.scnu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.mapper.ProductMapper;
import com.scnu.travel.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductMapper productMapper;


    //查询所有的产品，并封装到以Product为属性的页面对象中
    public Page<Product> findPage(int page,int size){
        Page<Product> productPage = productMapper.findProductPage(new Page(page, size));
        return productPage;
    }

    //增加新的产品
    public void add(Product product){
        productMapper.insert(product);
    }

    //根据产品的pid查询产品
    public Product findOne(int pid){
        return productMapper.findOne(pid);
    }

    //更新产品的信息
    public void update(Product product){
        productMapper.updateById(product);
    }

    //更新产品的状态
    public void updateStatus(int pid){
        Product product = productMapper.selectById(pid);
        product.setStatus(!product.getStatus());
        productMapper.updateById(product);
    }

    //搜索框和点击框
    public Page<Product> findProduct(Integer cid,String productName,int page,int size){
        QueryWrapper<Product> queryWrapper = new QueryWrapper();
        if (cid != null){
            queryWrapper.eq("cid",cid);
        }
        if (StringUtils.hasText(productName)){
            queryWrapper.like("productName",productName);
        }

        //还在启用的旅游产品
        queryWrapper.eq("status",1);
        //倒序排列
        queryWrapper.orderByDesc("pid");
        return productMapper.selectPage(new Page(page,size),queryWrapper);
    }

    //
}
