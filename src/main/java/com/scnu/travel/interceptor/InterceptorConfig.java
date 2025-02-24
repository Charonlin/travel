package com.scnu.travel.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象并指定拦截的路径
        registry.addInterceptor(new MemberInterceptor())
                .addPathPatterns("/frontdesk/favorite/**");
    }
}
