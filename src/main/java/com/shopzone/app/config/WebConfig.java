package com.shopzone.app.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${product.image.upload-dir:product-images}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        registry.addResourceHandler(
                "/product-images/**"
        )
        .addResourceLocations(
                "file:" +
                Paths.get(uploadDir)
                     .toAbsolutePath()
                     .toString()
                     + "/"
        );
    }
}