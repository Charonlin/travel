package com.scnu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scnu.travel.bean.Result;
import com.scnu.travel.mapper.MemberMapper;
import com.scnu.travel.pojo.Member;
import com.scnu.travel.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private MailUtils mailUtils;
    @Value("${project.path}")
    private String projectPath;

    //注册
    public Result register(Member member){
        //1.保存用户
        //验证用户名是否重复
        QueryWrapper<Member> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("username",member.getUsername());
        List<Member> members1 = memberMapper.selectList(queryWrapper1);
        if (members1.size() > 0) {
            return new Result(false,"用户名已存在");
        }
        //验证手机是否重复
        QueryWrapper<Member> queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("phoneNum",member.getPhoneNum());
        List<Member> members2 = memberMapper.selectList(queryWrapper2);
        if (members2.size() > 0) {
            return new Result(false,"手机号码已存在");
        }
        //验证邮箱是否重复
        QueryWrapper<Member> queryWrapper3 = new QueryWrapper();
        queryWrapper3.eq("email",member.getEmail());
        List<Member> members3 = memberMapper.selectList(queryWrapper3);
        if (members3.size() > 0) {
            return new Result(false,"邮箱已存在");
        }
        //加密密码
        String password = member.getPassword();
        String encode = encoder.encode(password);
        member.setPassword(encode);
        //设置用户状态为false
        member.setActive(false);

        //2.发送激活邮件
        String activeCode = UUID.randomUUID().toString();
        //给用户发送一篇激活邮件，该邮件包含了一个激活链接，链接中包含了激活码
        String activeUrl = projectPath+"/frontdesk/member/active?activeCode="+activeCode;
        String text = "恭喜您注册成功!<a href='"+activeUrl+"'>点击激活</a>，完成账号认证";
        System.out.println(text);
        mailUtils.sendMail(member.getEmail(),text,"用户注册激活!");
        //保存用户激活码
        member.setActiveCode(activeCode);
        //保存用户
        memberMapper.insert(member);
        return new Result(true,"注册成功!");
    }

    //激动用户
    public String active(String activeCode){
        //根据激活码查找用户
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("activeCode",activeCode);
        Member member = memberMapper.selectOne(queryWrapper);
        //没有找到用户代表激活失败
        if (member == null) {
            return "激活失败!";
        }else{
            member.setActive(true);
            memberMapper.updateById(member);
            return "激活成功，请<a href='"+projectPath+"/frontdesk/login'>登录</a>";
        }
    }

    //登录
    public Result login(String name,String password){
        Member member = null;
        //根据用户名进行查询
        if (member == null) {
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        //根据手机号码进行查询
        if (member == null) {
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phoneNum",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        //根据邮箱进行查询
        if (member == null) {
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("email",name);
            member = memberMapper.selectOne(queryWrapper);
        }
        //到这里member都还为null的话，说明没有该用户
        if (member == null){
            return new Result(false,"该用户不存在!");
        }

        boolean flag = encoder.matches(password, member.getPassword());
        if (!flag){
            return new Result(false,"用户名或密码错误!");
        }

        if (!member.isActive()) {
            return new Result(false,"该用户未激活，请登录邮箱激活用户!");
        }
        return new Result(true,"登录成功",member);
    }
}
