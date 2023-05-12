package com.example.campus.config;


import com.example.campus.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        //配置白名单list集合
        List <String> patterns = new ArrayList<>();
        patterns.add("/**");
        patterns.add("/css/**");
        patterns.add("/pages/js/**");
        patterns.add("/pages/login.html");
        patterns.add("/pages/guide.html");
        patterns.add("/pages/signup.html");
        patterns.add("/pages/test.html");
        patterns.add("/login");
        patterns.add("/user/signup");
        //拦截器注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
