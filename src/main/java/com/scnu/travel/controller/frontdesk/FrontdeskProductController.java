package com.scnu.travel.controller.frontdesk;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.mapper.ProductMapper;
import com.scnu.travel.pojo.Member;
import com.scnu.travel.pojo.Product;
import com.scnu.travel.service.FavoriteService;
import com.scnu.travel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/product")
public class FrontdeskProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FavoriteService favoriteService;

    /**
     * 查询旅游产品列表
     * @param cid   根据产品类型进行查询
     * @param productName 根据产品名称关键字进行模糊查询
     * @param page  页码数
     * @param size  页面条数
     * @return
     */
    @RequestMapping("/routeList")
    @ResponseBody
    public ModelAndView findProduct(Integer cid, String productName,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size){
        ModelAndView modelAndView = new ModelAndView();
        Page<Product> productPage = productService.findProduct(cid, productName, page, size);
        modelAndView.addObject("productPage",productPage);
        modelAndView.addObject("cid",cid);
        modelAndView.addObject("productName",productName);
        modelAndView.setViewName("/frontdesk/route_list");
        return modelAndView;
    }

    @RequestMapping("/routeDetail")
    public ModelAndView findOne(Integer pid, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findOne(pid);

        //查询用户是否收藏
        Object member = session.getAttribute("member");
        if (member == null) {//未登录表示为收藏
            modelAndView.addObject("favorite",false);
        }else{
            Member member1 = (Member) member;
//            boolean favorite = favoriteService.findFavorite(pid, member1.getMid());
//            modelAndView.addObject("favorite",favorite);
        }

        modelAndView.addObject("product",product);
        modelAndView.addObject("pid",pid);
        modelAndView.setViewName("/frontdesk/route_detail");
        return modelAndView;
    }
}
