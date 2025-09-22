package com.thorekt.chatop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Swagger configuration class
 * 
 * @author thorekt
 */
@Configuration
public class OpenApiConfig {

    public static final String BEARER_SCHEME = "Bearer";

    @Bean
    public OpenAPI chatopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ChaTop API").version("v1"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

    }
}
