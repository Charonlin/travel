package com.scnu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.pojo.Permission;
import com.scnu.travel.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/backstage/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/permission/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        Page<Permission> permissionPage = permissionService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissionPage",permissionPage);
        modelAndView.setViewName("/backstage/permission_all");
        return modelAndView;
    }

    @RequestMapping("/add")
    public String add(Permission permission){
        permissionService.addPermission(permission);
        return "redirect:/backstage/permission/all";
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam Integer pid){
        Permission permission = permissionService.findPermission(pid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permission",permission);
        modelAndView.addObject("pid",pid);
        modelAndView.setViewName("/backstage/permission_edit");
        return modelAndView;
    }

    @RequestMapping("/update")
    public String updatePermission(Permission permission){
        permissionService.updatePermission(permission);
        return "redirect:/backstage/permission/all";
    }

    @RequestMapping("/deletePermission")
    public String deletePermission(@RequestParam Integer pid){
        permissionService.deletePermission(pid);
        return "redirect:/backstage/permission/all";
    }
}
