package com.example.aicodereviewerpersonalityapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LlmClientConfig {

  @Bean
  public WebClient llmWebClient(WebClient.Builder builder, ReviewerProperties properties) {
    return builder.baseUrl(properties.getLlm().getBaseUrl()).build();
  }
}

