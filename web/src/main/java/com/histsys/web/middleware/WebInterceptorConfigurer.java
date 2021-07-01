package com.histsys.web.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebInterceptorConfigurer implements WebMvcConfigurer {

    /**
     * CROS 处理
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("authorization", "content-type")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE");
    }

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    /**
     * 添加自定义的参数拦截器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver); // 用户信息注入 + 角色判断识别
    }

}
