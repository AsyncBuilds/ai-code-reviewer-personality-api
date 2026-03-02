package com.example.aicodereviewerpersonalityapi.controller;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import com.example.aicodereviewerpersonalityapi.config.TraceIdUtil;
import com.example.aicodereviewerpersonalityapi.model.ConfigResponse;
import com.example.aicodereviewerpersonalityapi.model.HealthzResponse;
import com.example.aicodereviewerpersonalityapi.model.PersonalitiesResponse;
import com.example.aicodereviewerpersonalityapi.model.Personality;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MetaController {

  private final ReviewerProperties properties;

  public MetaController(ReviewerProperties properties) {
    this.properties = properties;
  }

  @GetMapping("/personalities")
  public PersonalitiesResponse personalities() {
    List<PersonalitiesResponse.PersonalityItem> items =
        Arrays.stream(Personality.values())
            .map(p -> new PersonalitiesResponse.PersonalityItem(p.name(), descriptionFor(p)))
            .toList();
    return new PersonalitiesResponse(items, TraceIdUtil.currentTraceId());
  }

  @GetMapping("/healthz")
  public HealthzResponse healthz() {
    return new HealthzResponse("ok", TraceIdUtil.currentTraceId());
  }

  @GetMapping("/config")
  public ConfigResponse config() {
    return new ConfigResponse(
        properties.getModelVersion(),
        properties.getMaxSnippetLength(),
        properties.isEnableSarcasm(),
        properties.isStrictSecurity(),
        properties.getWarningThreshold(),
        properties.getLlm().isEnabled(),
        properties.getLlm().getBaseUrl(),
        properties.getLlm().getModel(),
        TraceIdUtil.currentTraceId());
  }

  private String descriptionFor(Personality personality) {
    return switch (personality) {
      case STRICT -> "Direct and formal reviewer tone.";
      case PASSIVE_AGGRESSIVE -> "Snarky tone without insults or slurs.";
      case OVER_ENTHUSIASTIC -> "Very positive tone with light emoji usage.";
      case FAANG_INTERVIEWER -> "Focuses on edge cases, complexity, and tests.";
      case STARTUP_CTO -> "Pragmatic, shipping-focused, safety-aware feedback.";
    };
  }
}
