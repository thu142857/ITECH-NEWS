package com.itechnews.configuration;


import com.itechnews.interceptor.CheckingLoggedUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    CheckingLoggedUserInterceptor checkCurrentLoggedUserInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkCurrentLoggedUserInterceptor)
                .excludePathPatterns("/login")
                .addPathPatterns("/admin/**");
    }

}
