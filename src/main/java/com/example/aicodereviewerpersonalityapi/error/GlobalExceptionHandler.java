package com.example.aicodereviewerpersonalityapi.error;

import com.example.aicodereviewerpersonalityapi.config.TraceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(this::formatFieldError)
            .orElse("Validation failed");
    return build(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
      ConstraintViolationException ex, HttpServletRequest request) {
    String message = ex.getConstraintViolations().stream().findFirst().map(v -> v.getMessage()).orElse("Invalid input");
    return build(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiErrorResponse> handleNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST, "Malformed JSON request", request.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", request.getRequestURI());
  }

  @ExceptionHandler(WebClientResponseException.class)
  public ResponseEntity<ApiErrorResponse> handleUpstream(
      WebClientResponseException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_GATEWAY, "LLM provider error: " + ex.getStatusCode(), request.getRequestURI());
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiErrorResponse> handleState(IllegalStateException ex, HttpServletRequest request) {
    return build(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request.getRequestURI());
  }

  private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, String path) {
    ApiErrorResponse response =
        new ApiErrorResponse(
            OffsetDateTime.now(), status.value(), status.getReasonPhrase(), message, path, TraceIdUtil.currentTraceId());
    return ResponseEntity.status(status).body(response);
  }

  private String formatFieldError(FieldError fieldError) {
    return fieldError.getField() + ": " + fieldError.getDefaultMessage();
  }
}
