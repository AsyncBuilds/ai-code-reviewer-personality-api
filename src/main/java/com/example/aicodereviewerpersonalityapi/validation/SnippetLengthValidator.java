package com.example.aicodereviewerpersonalityapi.validation;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SnippetLengthValidator implements ConstraintValidator<ValidSnippetLength, String> {

  private final ReviewerProperties properties;

  public SnippetLengthValidator(ReviewerProperties properties) {
    this.properties = properties;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    int max = properties.getMaxSnippetLength();
    if (value.length() <= max) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate(
            "codeSnippet length must be <= " + max + " (reviewer.maxSnippetLength)")
        .addConstraintViolation();
    return false;
  }
}

