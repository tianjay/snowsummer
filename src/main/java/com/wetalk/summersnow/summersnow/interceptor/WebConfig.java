package com.wetalk.summersnow.summersnow.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc 此处删除注解，因为拦截静态资源的问题
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private  SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LocaleChangeInterceptor());
//        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
        //上面到下面的过程是一个配置INtercepter类后后，托管注入的过程
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }
}
