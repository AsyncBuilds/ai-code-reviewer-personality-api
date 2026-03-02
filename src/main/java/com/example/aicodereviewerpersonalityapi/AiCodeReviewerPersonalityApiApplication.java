package com.example.aicodereviewerpersonalityapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AiCodeReviewerPersonalityApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AiCodeReviewerPersonalityApiApplication.class, args);
  }
}

