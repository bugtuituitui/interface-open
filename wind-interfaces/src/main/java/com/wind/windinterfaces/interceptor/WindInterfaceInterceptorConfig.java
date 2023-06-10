package com.wind.windinterfaces.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kfg
 * @date 2023/6/9 21:35
 */
@Configuration
public class WindInterfaceInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private SubCountInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**");
    }
}
