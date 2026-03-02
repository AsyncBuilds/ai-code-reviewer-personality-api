# ai-code-reviewer-personality-api

Funny AI-themed Spring Boot demo API that reviews code snippets with reviewer personalities powered by a live OpenAI LLM call.

This is a **dummy Spring Boot demo API** created solely for testing purposes.  

Its primary goal is to serve as a sample repository for validating the GitHub Action  
👉 https://github.com/kavinlabs/confluence-pr-doc-sync  

The API itself is intentionally simple and exists only to trigger and test Confluence PR documentation sync workflows.

## Stack
- Java 17
- Spring Boot 3
- Maven
- springdoc-openapi (Swagger UI)
- Spring Boot Actuator

## Run
Set your OpenAI key first:
```bash
export OPENAI_API_KEY=your_key_here
```

```bash
mvn spring-boot:run
```

## Links
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- Actuator health: http://localhost:8080/actuator/health
- API healthz: http://localhost:8080/api/healthz

## API Examples

### 1) STRICT reviewer

#### Request
```bash
curl -X POST http://localhost:8080/api/review \
  -H "Content-Type: application/json" \
  -d "{\"codeSnippet\":\"try { work(); } catch (Exception e) {}\",\"language\":\"JAVA\",\"personality\":\"STRICT\"}"
```

#### Response
```json
{
  "verdict": "REQUEST_CHANGES",
  "comment": "Empty catch block detected\n- The catch block for Exception is empty, which can hide errors and complicate debugging\n- No logging or error handling is present\n- This can lead to silent failures and potential security risks\nSuggested next step: Implement proper error handling or logging within the catch block to ensure exceptions are not silently ignored.",
  "modelVersion": "v1.0",
  "traceId": "c3f13a9f-d58b-4a1c-9adf-19101b0fdd66"
}
```

---

### 2) PASSIVE_AGGRESSIVE reviewer

#### Request
```bash
curl -X POST http://localhost:8080/api/review \
  -H "Content-Type: application/json" \
  -d "{\"codeSnippet\":\"const apiKey='123'; // TODO rotate\",\"language\":\"JAVASCRIPT\",\"personality\":\"PASSIVE_AGGRESSIVE\"}"
```

#### Response
```json
{
  "verdict": "REQUEST_CHANGES",
  "comment": "Oh look, a hardcoded API key with a TODO comment. How original.\n- Hardcoded API key detected, which is a big no-no in security-conscious environments.\n- The TODO comment suggests rotation, but leaving secrets in code is a bad habit.\n- No other code context or tests mentioned.\nNext step: Remove the hardcoded API key immediately and use environment variables or secure vaults instead. Also, please rotate that key ASAP before someone else does it for you.",
  "modelVersion": "v1.0",
  "traceId": "2c361f21-06da-4b62-b1ce-20036856a7c0"
}
```

## Test
```bash
mvn test
```

The integration tests mock the LLM client, so tests do not require external network calls.
