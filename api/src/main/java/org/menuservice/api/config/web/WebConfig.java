package org.menuservice.api.config.web;

import org.menuservice.api.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    private List<String> OPEN_API = List.of(
            "/open-api/**",
            "/swagger-ui/**"
    );

    private List<String> DEFUALT_EXCLUDE = List.of(
            "/"
//            "favicon.ico",
//            "/error"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(OPEN_API)
                .excludePathPatterns(DEFUALT_EXCLUDE);
    }
}