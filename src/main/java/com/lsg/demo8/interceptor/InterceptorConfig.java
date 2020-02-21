package com.lsg.demo8.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private String[] excludePathPatterns;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        String[] pathPatterns = {"/**"};   //拦截器路径规则
        String[] excludePathPatterns = {"/","/login","/dologin","/css/**",
        "/fonts/**","/images/**","/js/**","/localcss/**","/localjs/**"};   //排除路径规则
        registry.addInterceptor(new AuthorizationInterceptor())   //注册拦截器
        .addPathPatterns(pathPatterns)   //添加路径规则
       .excludePathPatterns(excludePathPatterns);   //添加排除路径规则
   }
}
