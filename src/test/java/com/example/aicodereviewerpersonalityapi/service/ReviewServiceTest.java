package com.example.aicodereviewerpersonalityapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import com.example.aicodereviewerpersonalityapi.engine.OpenAiReviewClient;
import com.example.aicodereviewerpersonalityapi.model.Language;
import com.example.aicodereviewerpersonalityapi.model.Personality;
import com.example.aicodereviewerpersonalityapi.model.ReviewRequest;
import com.example.aicodereviewerpersonalityapi.model.ReviewResponse;
import com.example.aicodereviewerpersonalityapi.model.Verdict;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReviewServiceTest {

  @Test
  void llmSecurityReviewCanReturnRequestChanges() {
    OpenAiReviewClient client = Mockito.mock(OpenAiReviewClient.class);
    ReviewerProperties properties = new ReviewerProperties();
    ReviewService service = new ReviewService(client, properties);

    ReviewRequest req = new ReviewRequest();
    req.setCodeSnippet("const apiKey='plaintext';");
    req.setLanguage(Language.JAVASCRIPT);
    req.setPersonality(Personality.STRICT);

    when(client.review(req))
        .thenReturn(
            new OpenAiReviewClient.LlmReviewResult(
                Verdict.REQUEST_CHANGES, "Summary: secret exposed.\n\nFindings:\n- hardcoded key\n\nSuggested next step: remove it."));

    ReviewResponse response = service.review(req, "trace-1");
    assertThat(response.getVerdict()).isEqualTo(Verdict.REQUEST_CHANGES);
    assertThat(response.getTraceId()).isEqualTo("trace-1");
  }
}

