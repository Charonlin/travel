package com.scnu.travel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义登录页面的信息
        http.formLogin()
                .loginPage("/backstage/admin_login")//自定义的登录界面
                .usernameParameter("username")//自定义界面中传上来的name属性值为username的input标签的内容
                .passwordParameter("password")//自定义界面中传上来的name属性值为password的input标签的内容
                .loginProcessingUrl("/backstage/admin/login")//⑥自定义登录页面表单提交的定位点
                .successForwardUrl("/backstage/index")
                .failureForwardUrl("/backstage/admin_fail");

        //自定义权限
        http.authorizeRequests()
                .antMatchers("/backstage/admin/login").permitAll()//登录不需要认证
                .antMatchers("/backstage/admin_login").permitAll()//登录不需要认证
                .antMatchers("/backstage/admin_fail").permitAll()//登录失败不需要认证
                .antMatchers("/**/*.css","/**/*.js").permitAll()//放行静态资源
                .antMatchers("/backstage/**").authenticated();//其他的都需要认证

        //自定义退出功能
        http.logout()
                .logoutUrl("/backstage/admin/logout")//退出登录的路径
                .logoutSuccessUrl("/backstage/admin_login")//退出登录成功后返回的页面
                .clearAuthentication(true)//退出登录后清除认证信息
                .invalidateHttpSession(true);//退出登录后清除session

        //异常处理
        http.exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler());

        //关闭csrf防护
        http.csrf().disable();

        //开启跨域访问
        http.cors();

    }

    //自定义密码编译器
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
