package com.example.aicodereviewerpersonalityapi.model;

public class HealthzResponse {
  private String status;
  private String traceId;

  public HealthzResponse(String status, String traceId) {
    this.status = status;
    this.traceId = traceId;
  }

  public String getStatus() {
    return status;
  }

  public String getTraceId() {
    return traceId;
  }
}

