package com.example.aicodereviewerpersonalityapi.engine;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.aicodereviewerpersonalityapi.config.ReviewerProperties;
import com.example.aicodereviewerpersonalityapi.model.Language;
import com.example.aicodereviewerpersonalityapi.model.Personality;
import com.example.aicodereviewerpersonalityapi.model.ReviewRequest;
import org.junit.jupiter.api.Test;

class ReviewPromptBuilderTest {

  @Test
  void strictAndEnthusiasticPromptsDifferInToneInstruction() {
    ReviewPromptBuilder builder = new ReviewPromptBuilder(new ReviewerProperties());

    ReviewRequest strictReq = new ReviewRequest();
    strictReq.setLanguage(Language.JAVA);
    strictReq.setPersonality(Personality.STRICT);
    strictReq.setCodeSnippet("class A {}");

    ReviewRequest enthusiasticReq = new ReviewRequest();
    enthusiasticReq.setLanguage(Language.JAVA);
    enthusiasticReq.setPersonality(Personality.OVER_ENTHUSIASTIC);
    enthusiasticReq.setCodeSnippet("class A {}");

    String strictPrompt = builder.buildUserPrompt(strictReq);
    String enthusiasticPrompt = builder.buildUserPrompt(enthusiasticReq);

    assertThat(strictPrompt).contains("direct and formal");
    assertThat(enthusiasticPrompt).contains("overly positive with at most 1-2 emojis");
    assertThat(strictPrompt).isNotEqualTo(enthusiasticPrompt);
  }
}
