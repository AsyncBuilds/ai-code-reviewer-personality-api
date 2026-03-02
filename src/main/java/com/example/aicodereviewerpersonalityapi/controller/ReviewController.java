package com.example.aicodereviewerpersonalityapi.controller;

import com.example.aicodereviewerpersonalityapi.config.TraceIdUtil;
import com.example.aicodereviewerpersonalityapi.model.ReviewRequest;
import com.example.aicodereviewerpersonalityapi.model.ReviewResponse;
import com.example.aicodereviewerpersonalityapi.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Review API")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/review")
  @Operation(
      summary = "Review a code snippet with a selected personality",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      schema = @Schema(implementation = ReviewRequest.class),
                      examples =
                          @ExampleObject(
                              name = "Basic request",
                              value =
                                  """
                                  {
                                    "codeSnippet": "try { foo(); } catch (Exception e) {}",
                                    "language": "JAVA",
                                    "personality": "STRICT"
                                  }
                                  """))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Review result",
            content =
                @Content(
                    schema = @Schema(implementation = ReviewResponse.class),
                    examples =
                        @ExampleObject(
                            value =
                                """
                                {
                                  "verdict": "COMMENT",
                                  "comment": "Summary: ...",
                                  "modelVersion": "v1.0",
                                  "traceId": "9c483f8b-5b4e-4cb0-b35e-cfbe4c1ee194"
                                }
                                """))),
        @ApiResponse(responseCode = "400", description = "Validation error")
      })
  public ReviewResponse review(@Valid @RequestBody ReviewRequest request) {
    return reviewService.review(request, TraceIdUtil.currentTraceId());
  }
}

