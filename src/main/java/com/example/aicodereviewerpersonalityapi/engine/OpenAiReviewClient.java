package com.example.aicodereviewerpersonalityapi.engine;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import com.example.aicodereviewerpersonalityapi.model.ReviewRequest;
import com.example.aicodereviewerpersonalityapi.model.Verdict;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OpenAiReviewClient {

  public record LlmReviewResult(Verdict verdict, String comment) {}

  private final WebClient llmWebClient;
  private final ReviewerProperties properties;
  private final ReviewPromptBuilder promptBuilder;
  private final ObjectMapper objectMapper;

  public OpenAiReviewClient(
      WebClient llmWebClient,
      ReviewerProperties properties,
      ReviewPromptBuilder promptBuilder,
      ObjectMapper objectMapper) {
    this.llmWebClient = llmWebClient;
    this.properties = properties;
    this.promptBuilder = promptBuilder;
    this.objectMapper = objectMapper;
  }

  public LlmReviewResult review(ReviewRequest request) {
    if (!properties.getLlm().isEnabled()) {
      throw new IllegalStateException("LLM review is disabled by configuration");
    }
    String apiKey = properties.getLlm().getApiKey();
    if (apiKey == null || apiKey.isBlank()) {
      throw new IllegalStateException("OPENAI API key is missing (reviewer.llm.apiKey)");
    }

    Map<String, Object> body =
        Map.of(
            "model", properties.getLlm().getModel(),
            "temperature", 0.1,
            "response_format", Map.of("type", "json_object"),
            "messages",
                List.of(
                    Map.of("role", "system", "content", promptBuilder.systemPrompt()),
                    Map.of("role", "user", "content", promptBuilder.buildUserPrompt(request))));

    JsonNode root =
        llmWebClient
            .post()
            .uri("/chat/completions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block(Duration.ofMillis(properties.getLlm().getTimeoutMs()));

    if (root == null) {
      throw new IllegalStateException("LLM returned empty response");
    }

    String content = root.path("choices").path(0).path("message").path("content").asText("");
    if (content.isBlank()) {
      throw new IllegalStateException("LLM returned empty completion content");
    }

    try {
      JsonNode parsed = objectMapper.readTree(content);
      Verdict verdict = safeVerdict(parsed.path("verdict").asText());
      String comment = parsed.path("comment").asText("No comment provided by model.");
      return new LlmReviewResult(verdict, comment);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to parse LLM JSON response", ex);
    }
  }

  private Verdict safeVerdict(String value) {
    try {
      return Verdict.valueOf(value);
    } catch (Exception ignored) {
      return Verdict.COMMENT;
    }
  }
}

