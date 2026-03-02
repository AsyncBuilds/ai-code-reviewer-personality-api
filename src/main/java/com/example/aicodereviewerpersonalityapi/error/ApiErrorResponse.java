package com.example.aicodereviewerpersonalityapi.error;

import java.time.OffsetDateTime;

public class ApiErrorResponse {
  private OffsetDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
  private String traceId;

  public ApiErrorResponse(
      OffsetDateTime timestamp, int status, String error, String message, String path, String traceId) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
    this.traceId = traceId;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public String getTraceId() {
    return traceId;
  }
}

