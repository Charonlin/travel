package com.scnu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.pojo.Admin;
import com.scnu.travel.pojo.Category;
import com.scnu.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/category/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        ModelAndView modelAndView = new ModelAndView();
        //调用adminService的findPage方法，返回Page对象
        Page<Category> categoryPage = categoryService.findPage(page, size);
        /**
         * 参数一：可以让前端页面用来获取对象
         * 参数二：将返回的Page对象存在modelAndView中
         */
        modelAndView.addObject("categoryPage",categoryPage);
        //表示可以传到category_all这个页面
        modelAndView.setViewName("/backstage/category_all");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Category category){
        categoryService.add(category);
        return "redirect:/backstage/category/all";
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam Integer cid){
        Category category = categoryService.findById(cid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category",category);
        modelAndView.addObject("cid",cid);
        modelAndView.setViewName("/backstage/category_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Category category){
        categoryService.update(category);
        return "redirect:/backstage/category/all";
    }

    @RequestMapping("/delete")
    public String delete(Integer cid){
        categoryService.delete(cid);
        return "redirect:/backstage/category/all";
    }
}
