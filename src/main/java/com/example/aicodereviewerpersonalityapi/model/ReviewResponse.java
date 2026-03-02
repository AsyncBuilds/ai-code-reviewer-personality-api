package com.example.aicodereviewerpersonalityapi.model;

public class ReviewResponse {
  private Verdict verdict;
  private String comment;
  private String modelVersion;
  private String traceId;

  public ReviewResponse(Verdict verdict, String comment, String modelVersion, String traceId) {
    this.verdict = verdict;
    this.comment = comment;
    this.modelVersion = modelVersion;
    this.traceId = traceId;
  }

  public Verdict getVerdict() {
    return verdict;
  }

  public String getComment() {
    return comment;
  }

  public String getModelVersion() {
    return modelVersion;
  }

  public String getTraceId() {
    return traceId;
  }
}

