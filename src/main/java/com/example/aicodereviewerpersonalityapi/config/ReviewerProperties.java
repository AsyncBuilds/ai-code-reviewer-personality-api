package com.example.aicodereviewerpersonalityapi.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "reviewer")
public class ReviewerProperties {
  @NotBlank
  private String modelVersion = "v1.0";

  @Min(2)
  private int maxSnippetLength = 6000;

  private boolean enableSarcasm = true;
  private boolean strictSecurity = true;

  @Min(1)
  private int warningThreshold = 2;
  private final Llm llm = new Llm();

  public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  public int getMaxSnippetLength() {
    return maxSnippetLength;
  }

  public void setMaxSnippetLength(int maxSnippetLength) {
    this.maxSnippetLength = maxSnippetLength;
  }

  public boolean isEnableSarcasm() {
    return enableSarcasm;
  }

  public void setEnableSarcasm(boolean enableSarcasm) {
    this.enableSarcasm = enableSarcasm;
  }

  public boolean isStrictSecurity() {
    return strictSecurity;
  }

  public void setStrictSecurity(boolean strictSecurity) {
    this.strictSecurity = strictSecurity;
  }

  public int getWarningThreshold() {
    return warningThreshold;
  }

  public void setWarningThreshold(int warningThreshold) {
    this.warningThreshold = warningThreshold;
  }

  public Llm getLlm() {
    return llm;
  }

  public static class Llm {
    private boolean enabled = true;
    @NotBlank private String baseUrl = "https://api.openai.com/v1";
    @NotBlank private String model = "gpt-4.1-mini";
    private String apiKey = "";
    @Min(1000)
    private int timeoutMs = 20000;

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }

    public String getModel() {
      return model;
    }

    public void setModel(String model) {
      this.model = model;
    }

    public String getApiKey() {
      return apiKey;
    }

    public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
    }

    public int getTimeoutMs() {
      return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
      this.timeoutMs = timeoutMs;
    }
  }
}
