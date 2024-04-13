package com.scnu.travel.controller.frontdesk;

import com.scnu.travel.bean.Result;
import com.scnu.travel.pojo.Member;
import com.scnu.travel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/frontdesk/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     *  参数一：表单提交的数据封装成member对象
     *  参数二：表单中提交的用户输入的验证码checkCode
     *  参数三：session会话对象
     */

    /**
     * 表达对于此时返回值用ModelAndView的理解：
     *      如果在用户注册过程中信息出现填写错误，则可以用ModelAndView将错误信息带回到我们的注册页面，从而进行显示出来
     */
    @RequestMapping("/register")
    public ModelAndView register(Member member, String checkCode, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        //判断验证码是否正确
        //通过getAttribute方法获取工具类CheckCodeServlet生成的验证码，用来和用户输入的验证码进行比对
        String sessionCheckCode = (String) session.getAttribute("checkCode");//这个checkCode是工具类中setAttribute存入的验证码名称
        if (!sessionCheckCode.equalsIgnoreCase(checkCode)) {//通常验证码会忽略大小写
            modelAndView.addObject("message","验证码是错误的...");
            modelAndView.setViewName("/frontdesk/register");
            return modelAndView;
        }
        //注册
        Result result = memberService.register(member);
        if (!result.isFlag()) {//注册失败
            modelAndView.addObject("message",result.getMessage());
            modelAndView.setViewName("/frontdesk/register");
        }else{//注册成功
            modelAndView.setViewName("/frontdesk/register_ok");
        }
        return modelAndView;
    }

    @RequestMapping("/active")
    public ModelAndView active(String activeCode){
        ModelAndView modelAndView = new ModelAndView();
        String active = memberService.active(activeCode);
        modelAndView.addObject("message",active);
        modelAndView.setViewName("/frontdesk/active_result");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login(String name,String password,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Result result = memberService.login(name, password);
        if (!result.isFlag()){
            modelAndView.addObject("message",result.getMessage());
            modelAndView.setViewName("/frontdesk/login");
        }else{
            //登录成功,设置 session
            session.setAttribute("member",result.getData());
            modelAndView.setViewName("redirect:/frontdesk/index");
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("member");
        return "redirect:/frontdesk/index";
    }
}
