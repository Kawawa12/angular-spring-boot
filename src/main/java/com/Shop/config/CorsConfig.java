package com.Shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allowing CORS for all routes
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*");

                // Allowing CORS specifically for static image paths
                registry.addMapping("/images/**")
                        .allowedOrigins("http://localhost:4200") // Adjust the Angular URL if needed
                        .allowedMethods("GET", "POST");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Serve static files from /src/main/resources/static/images/
                registry.addResourceHandler("/images/**")
                        .addResourceLocations("classpath:/static/images/");
            }
        };
    }
}
