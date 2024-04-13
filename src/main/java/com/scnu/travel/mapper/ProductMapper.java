package com.scnu.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

public interface ProductMapper extends BaseMapper<Product> {
    Page<Product> findProductPage(Page<Product> page);

    Product findOne(int pid);

    int findFavoritePidAndMid(@Param("pid")Integer pid, @Param("mid")Integer mid);

//    Page<Product> findMemberFavorite(Page<Product> page,Integer mid);
//
//    void addFavorite(@Param("pid")Integer pid, @Param("mid")Integer mid);
//
//    void delFavorite(@Param("pid")Integer pid, @Param("mid")Integer mid);
}
