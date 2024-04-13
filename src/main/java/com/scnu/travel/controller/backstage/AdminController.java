package com.scnu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.bean.RoleWithStatus;
import com.scnu.travel.pojo.Admin;
import com.scnu.travel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //modelAndView 既可以返回页面，也可以返回数据
    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/admin/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        //创建一个接收对象
        ModelAndView modelAndView = new ModelAndView();
        //调用adminService的findPage方法，返回Page对象
        Page<Admin> adminPage = adminService.findPage(page, size);
        /**
         * 参数一：可以让前端页面用来获取对象
         * 参数二：将返回的Page对象存在modelAndView中
         */
        modelAndView.addObject("adminPage",adminPage);
        //表示可以传到amdin_all这个页面
        modelAndView.setViewName("/backstage/admin_all");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String addAdmin(Admin admin){
        adminService.add(admin);
        return "redirect:/backstage/admin/all";
    }

    //查询管理员，跳转到修改页面
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam Integer aid){
        ModelAndView modelAndView = new ModelAndView();
        Admin admin = adminService.findById(aid);
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "redirect:/backstage/admin/all";
    }

    @RequestMapping("/desc")
    public ModelAndView desc(@RequestParam Integer aid){
        ModelAndView modelAndView = new ModelAndView();
        Admin admin = adminService.findDesc(aid);
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("/backstage/admin_desc");
        return modelAndView;
    }

    @RequestMapping("/findRole")
    public ModelAndView findRole(@RequestParam Integer aid){
        ModelAndView modelAndView = new ModelAndView();
        List<RoleWithStatus> roles = adminService.findRole(aid);
        modelAndView.addObject("roles",roles);
        modelAndView.addObject("aid",aid);
        modelAndView.setViewName("/backstage/admin_findRole");
        return modelAndView;
    }

    @RequestMapping("/updateRole")
    public String updateRole(Integer aid,Integer[] ids){
        adminService.updateAdminRole(aid,ids);
        return "redirect:/backstage/admin/all";
    }

    @RequestMapping("/updateStatus")
    public String updateStatus(Integer aid){
        adminService.updateStatus(aid);
        return "redirect:/backstage/admin/all";
    }
}
