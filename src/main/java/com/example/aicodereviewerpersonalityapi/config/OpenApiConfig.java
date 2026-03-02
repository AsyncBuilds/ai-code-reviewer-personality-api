package com.example.aicodereviewerpersonalityapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI reviewerOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("AI Code Reviewer Personality API")
                .version("v1.0")
                .description("Deterministic, personality-driven code review demo API"));
  }
}

