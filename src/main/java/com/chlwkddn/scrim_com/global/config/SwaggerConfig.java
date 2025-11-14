package com.chlwkddn.scrim_com.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI scrimOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Naejeon API")
                        .description("League of Legends 내전")
                        .version("v1.0.0"));
    }
}