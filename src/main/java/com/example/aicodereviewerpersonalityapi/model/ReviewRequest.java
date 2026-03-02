package com.example.aicodereviewerpersonalityapi.model;

import com.example.aicodereviewerpersonalityapi.validation.ValidSnippetLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewRequest {

  @NotBlank(message = "codeSnippet is required")
  @Size(min = 2, message = "codeSnippet must be at least 2 characters")
  @ValidSnippetLength
  private String codeSnippet;

  @NotNull(message = "language is required")
  private Language language;

  @NotNull(message = "personality is required")
  private Personality personality;

  public String getCodeSnippet() {
    return codeSnippet;
  }

  public void setCodeSnippet(String codeSnippet) {
    this.codeSnippet = codeSnippet;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Personality getPersonality() {
    return personality;
  }

  public void setPersonality(Personality personality) {
    this.personality = personality;
  }
}

