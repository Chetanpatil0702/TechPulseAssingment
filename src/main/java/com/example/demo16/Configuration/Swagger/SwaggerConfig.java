package com.example.demo16.Configuration.Swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("TechPulse Assingment")
                        .version("1.0")
                        .description("API Documentation For TechPulse Custom Role And Permission (Using JWT) Base Project"));
    }
}
