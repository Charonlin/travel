package com.scnu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.bean.WangEditorResult;
import com.scnu.travel.mapper.ProductMapper;
import com.scnu.travel.pojo.Category;
import com.scnu.travel.pojo.Product;
import com.scnu.travel.service.CategoryService;
import com.scnu.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/backstage/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/product/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Page<Product> productPage = productService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("productPage", productPage);
        modelAndView.setViewName("/backstage/product_all");
        return modelAndView;
    }

    @RequestMapping("/addProduct")
    public ModelAndView addProduct(){
        List<Category> categoryList = categoryService.findAllCategory();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("/backstage/product_add");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Product product){
        productService.add(product);
        return "redirect:/backstage/product/all";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public WangEditorResult upload(HttpServletRequest request, MultipartFile file) throws Exception {
        //创建文件夹，存放上传文件
        //1.设置上传文件夹的真实路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/upload";
        System.out.println(realPath);
        //2.判断该文件夹是否存在，如果不存在，新建文件夹
        File dir= new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //拿到上传的文件名
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        filename = UUID.randomUUID() + filename;
        //创建空文件
        File newFile = new File(dir, filename);
        //将上传的文件写到空文件中
        file.transferTo(newFile);

        //构造返回的结果
        WangEditorResult wangEditorResult = new WangEditorResult();
        wangEditorResult.setErrno(0);
        String[] data = {"/upload/" + filename};
        wangEditorResult.setData(data);
        return wangEditorResult;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(Integer pid){
        //查询将被修改的产品
        Product product = productService.findOne(pid);
        //查询产品的所有类别
        List<Category> categoryList = categoryService.findAllCategory();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pid",pid);
        modelAndView.addObject("product",product);
        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName("/backstage/product_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/backstage/product/all";
    }

    //@RequestHeader表示修改完后跳转到之前的页面
    @RequestMapping("/updateStatus")
    public String updateStatus(Integer pid,@RequestHeader("Referer") String referer){
        productService.updateStatus(pid);
        return "redirect:"+referer;
    }
}
