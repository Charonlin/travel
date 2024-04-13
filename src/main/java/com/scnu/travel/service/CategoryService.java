package com.scnu.travel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.mapper.CategoryMapper;
import com.scnu.travel.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //分页查看
    public Page<Category> findPage(int page,int size){
        Page categoryPage = categoryMapper.selectPage(new Page(page, size), null);
        return categoryPage;
    }

    //增加产品类型
    public void add(Category category){
        categoryMapper.insert(category);
    }

    //删除产品类型
    public void delete(Integer cid){
        categoryMapper.deleteById(cid);
    }

    //根据id查产品类型
    public Category findById(Integer cid){
        Category category = categoryMapper.selectById(cid);
        return category;
    }

    public void update(Category category){
        categoryMapper.updateById(category);
    }

    //查询所有的产品类型
    public List<Category> findAllCategory(){
        return categoryMapper.selectList(null);
    }
}
