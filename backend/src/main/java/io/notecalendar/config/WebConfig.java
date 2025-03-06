package io.notecalendar.Config;

import io.notecalendar.Interceptor.LoginInterceptor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// TODO add interceptor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

//    allowed origins in application.yml
    @Autowired
    private AllowedOriginsConfig allowedOriginsConfig;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .AllowedOriginsConfig("http://localhost:5174")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowCredentials(true)
//                .allowedHeaders("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"); // 顯式允許 Authorization 頭部
//    }

//     處理 OPTIONS 預檢請求
    @Bean
    public Filter corsFilter() {
        return (request, response, chain) -> {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String origin = req.getHeader("Origin");
            if (allowedOriginsConfig.getAllowed().contains(origin)) {
                res.setHeader("Access-Control-Allow-Origin", origin);
            } else {
                System.out.println("Access-Control-Allow-Origin: " + origin + " is not allowed");
            }
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, Origin");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Max-Age", "3600");
            if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                chain.doFilter(request, response);
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user", "/user/login", "/user/register", "/user/token");
    }
}
