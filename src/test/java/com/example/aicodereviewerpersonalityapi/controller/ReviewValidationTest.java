package com.example.aicodereviewerpersonalityapi.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "reviewer.maxSnippetLength=10")
class ReviewValidationTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void maxSnippetLengthReturns400WithErrorShape() throws Exception {
    String payload =
        """
        {
          "codeSnippet":"01234567890123456789",
          "language":"JAVA",
          "personality":"STRICT"
        }
        """;

    mockMvc
        .perform(post("/api/review").contentType("application/json").content(payload))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.path").value("/api/review"))
        .andExpect(jsonPath("$.traceId", notNullValue()));
  }
}

