package com.thorekt.chatop.configuration;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for file uploads
 * 
 * @author thorekt
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {

    @Value("${app.uploads.base-dir}")
    private String baseDir;

    @Value("${app.uploads.base-url}")
    private String baseUrl;

    /**
     * Configure resource handlers to serve uploaded files
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var basePath = Path.of(baseDir).toAbsolutePath().normalize();

        String location = basePath.toUri().toString();
        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler(baseUrl + "/**")
                .addResourceLocations(location);
    }
}
