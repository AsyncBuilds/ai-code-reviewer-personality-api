package com.example.aicodereviewerpersonalityapi.model;

public class ConfigResponse {
  private String modelVersion;
  private int maxSnippetLength;
  private boolean enableSarcasm;
  private boolean strictSecurity;
  private int warningThreshold;
  private boolean llmEnabled;
  private String llmBaseUrl;
  private String llmModel;
  private String traceId;

  public ConfigResponse(
      String modelVersion,
      int maxSnippetLength,
      boolean enableSarcasm,
      boolean strictSecurity,
      int warningThreshold,
      boolean llmEnabled,
      String llmBaseUrl,
      String llmModel,
      String traceId) {
    this.modelVersion = modelVersion;
    this.maxSnippetLength = maxSnippetLength;
    this.enableSarcasm = enableSarcasm;
    this.strictSecurity = strictSecurity;
    this.warningThreshold = warningThreshold;
    this.llmEnabled = llmEnabled;
    this.llmBaseUrl = llmBaseUrl;
    this.llmModel = llmModel;
    this.traceId = traceId;
  }

  public String getModelVersion() {
    return modelVersion;
  }

  public int getMaxSnippetLength() {
    return maxSnippetLength;
  }

  public boolean isEnableSarcasm() {
    return enableSarcasm;
  }

  public boolean isStrictSecurity() {
    return strictSecurity;
  }

  public int getWarningThreshold() {
    return warningThreshold;
  }

  public boolean isLlmEnabled() {
    return llmEnabled;
  }

  public String getLlmBaseUrl() {
    return llmBaseUrl;
  }

  public String getLlmModel() {
    return llmModel;
  }

  public String getTraceId() {
    return traceId;
  }
}
