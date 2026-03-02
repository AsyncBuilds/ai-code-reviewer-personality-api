package com.example.aicodereviewerpersonalityapi.service;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import com.example.aicodereviewerpersonalityapi.engine.OpenAiReviewClient;
import com.example.aicodereviewerpersonalityapi.model.ReviewRequest;
import com.example.aicodereviewerpersonalityapi.model.ReviewResponse;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final OpenAiReviewClient openAiReviewClient;
  private final ReviewerProperties properties;

  public ReviewService(OpenAiReviewClient openAiReviewClient, ReviewerProperties properties) {
    this.openAiReviewClient = openAiReviewClient;
    this.properties = properties;
  }

  public ReviewResponse review(ReviewRequest request, String traceId) {
    OpenAiReviewClient.LlmReviewResult result = openAiReviewClient.review(request);
    return new ReviewResponse(result.verdict(), result.comment(), properties.getModelVersion(), traceId);
  }
}
