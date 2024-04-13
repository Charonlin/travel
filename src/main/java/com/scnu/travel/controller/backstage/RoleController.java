package com.scnu.travel.controller.backstage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scnu.travel.bean.PermissionWithStatus;
import com.scnu.travel.pojo.Role;
import com.scnu.travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/backstage/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/all")
    @PreAuthorize("hasAnyAuthority('/role/all')")
    public ModelAndView all(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size){
        /**
         * 通过返回ModelAndView来返回封装的对象顺便定位到了role_all页面
         */
        Page<Role> rolePage = roleService.findPage(page, size);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("rolePage",rolePage);
        modelAndView.setViewName("/backstage/role_all");
        return modelAndView;
    }

    //新建角色种类
    @RequestMapping("/add")
    public String addRole(Role role){
        roleService.addRole(role);
        return "redirect:/backstage/role/all";
    }

    //跳转到role_edit页面，并带上已有的信息
    @RequestMapping("/edit")
    public ModelAndView editRole(@RequestParam Integer rid){
        Role role = roleService.findRole(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role",role);
        modelAndView.setViewName("/backstage/role_edit");
        return modelAndView;
    }

    //跳转到role_all页面
    @RequestMapping("/update")
    public String updateEdit(Role role){
        roleService.update(role);
        return "redirect:/backstage/role/all";
    }

    //跳转到详情“页面”前的控制器
    @RequestMapping("/desc")
    public ModelAndView desc(@RequestParam Integer rid){
        Role role = roleService.findDesc(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role",role);
        modelAndView.setViewName("/backstage/role_desc");
        return modelAndView;
    }

    @RequestMapping("/findPermission")
    public ModelAndView findPermission(Integer rid){
        List<PermissionWithStatus> permissions = roleService.findPermission(rid);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("permissions",permissions);
        modelAndView.addObject("rid",rid);
        modelAndView.setViewName("/backstage/role_permission");
        return modelAndView;
    }

    @RequestMapping("/updatePermission")
    public String updateRolePermission(Integer rid,Integer[] ids){
        roleService.updateRolePermission(rid,ids);
        return "redirect:/backstage/role/all";
    }

    @RequestMapping("/deleteRole")
    public String deleteRole(@RequestParam Integer rid){
        roleService.deleteRoleByRid(rid);
        return "redirect:/backstage/role/all";
    }
}
