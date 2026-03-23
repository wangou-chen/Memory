// 把这里的包名改成你自己的（复制启动类的包名）
package com.wangou.memory;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 这个注解表示这是一个配置类，Spring 会自动加载
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // 配置跨域规则
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 对所有接口生效（/** 表示匹配所有路径）
        registry.addMapping("/**")
                // 允许所有前端域名访问（解决不同端口/域名的跨域）
                .allowedOriginPatterns("*")
                // 允许前端用 GET/POST/PUT/DELETE 等方法请求
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许前端携带所有请求头（比如 Token）
                .allowedHeaders("*")
                // 允许携带 Cookie 等凭证
                .allowCredentials(true)
                // 预检请求缓存1小时，减少重复请求
                .maxAge(3600);
    }
}