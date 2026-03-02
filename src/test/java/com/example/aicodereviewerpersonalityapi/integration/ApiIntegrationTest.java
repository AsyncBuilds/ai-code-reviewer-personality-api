package com.example.aicodereviewerpersonalityapi.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.aicodereviewerpersonalityapi.engine.OpenAiReviewClient;
import com.example.aicodereviewerpersonalityapi.model.Verdict;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private OpenAiReviewClient openAiReviewClient;

  @Test
  void postReviewReturnsExpectedFields() throws Exception {
    when(openAiReviewClient.review(any()))
        .thenReturn(
            new OpenAiReviewClient.LlmReviewResult(
                Verdict.COMMENT, "Summary: LLM review.\n\nFindings:\n- Sample finding\n\nSuggested next step: patch it."));

    String payload =
        """
        {
          "codeSnippet":"public class Demo { void ok() { System.out.println(1); } }",
          "language":"JAVA",
          "personality":"STARTUP_CTO"
        }
        """;

    mockMvc
        .perform(post("/api/review").contentType("application/json").content(payload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.verdict").exists())
        .andExpect(jsonPath("$.comment").exists())
        .andExpect(jsonPath("$.comment").value(matchesPattern("(?s).{11,}")))
        .andExpect(jsonPath("$.modelVersion").value("v1.0"))
        .andExpect(jsonPath("$.traceId", notNullValue()));
  }

  @Test
  void personalitiesEndpointReturnsExpectedList() throws Exception {
    mockMvc
        .perform(get("/api/personalities"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", hasSize(5)))
        .andExpect(jsonPath("$.items[0].name").value("STRICT"))
        .andExpect(jsonPath("$.traceId", notNullValue()));
  }

  @Test
  void errorResponsesIncludeTraceId() throws Exception {
    mockMvc
        .perform(post("/api/review").contentType("application/json").content("{\"language\":\"JAVA\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.traceId", notNullValue()));
  }
}
