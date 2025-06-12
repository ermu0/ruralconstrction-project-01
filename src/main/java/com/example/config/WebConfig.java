package com.example.config;

import com.example.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web服务配置类
 */

@Configuration //用于标识当前类是一个配置类（至于这个配置类有哪些配置功能，就要看这个类继承实现的是哪个类了）
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LoginCheckInterceptor loginCheckInterceptor;


    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**");//将LoginCheckInterceptor注册为拦截器
    }
}
