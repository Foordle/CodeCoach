package com.example.CodeCoach.repository;

import com.example.CodeCoach.repository.dto.GeminiRequest;
import com.example.CodeCoach.repository.dto.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Repository
public class AiEvaluationRepository {

    // @Value 필드를 제거하고 final로 선언합니다.
    private final String apiKey;
    private final String apiBaseUrl;
    private final WebClient webClient;

    // 수정된 생성자: 모든 의존성을 매개변수로 주입받습니다.
    public AiEvaluationRepository(WebClient webClient,
                                  @Value("${ai.gemini.key}") String apiKey,
                                  @Value("${ai.gemini.base-url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
    }

    /**
     * Gemini API에 코드 평가 요청을 보냅니다. (재시도 로직 포함)
     */
    public String requestAiFeedback(String modelName, Float temperature, Float topP, String fullPrompt) {

        // 1. 응답 JSON 스키마 정의
        Map<String, GeminiRequest.Property> properties = Map.of(
                "score", GeminiRequest.Property.builder().type("INTEGER").description("The final score from 0 to 100.").build(),
                "feedback", GeminiRequest.Property.builder().type("STRING").description("Detailed feedback and suggestions for the code.").build()
        );

        GeminiRequest.ResponseSchema schema = GeminiRequest.ResponseSchema.builder()
                .type("OBJECT")
                .properties(properties)
                .required(List.of("score", "feedback"))
                .build();

        // 2. 요청 객체 생성 (fullPrompt 및 파라미터 반영)
        GeminiRequest request = GeminiRequest.builder()
                .contents(List.of(
                        GeminiRequest.Content.builder()
                                .role("user")
                                .parts(List.of(GeminiRequest.Part.builder().text(fullPrompt).build()))
                                .build()
                ))
                .generationConfig(GeminiRequest.GenerationConfig.builder()
                        .responseMimeType("application/json")
                        .responseSchema(schema)
                        .temperature(temperature)
                        .topP(topP)
                        .build())
                .build();

        // 3. 최종 URL 조립
        String finalApiUrl = String.format(apiBaseUrl, modelName) + "?key=" + apiKey;

        // 4. WebClient를 사용하여 API 호출 (재시도 로직 적용)
        try {
            GeminiResponse response = webClient.post()
                    .uri(finalApiUrl)
                    .bodyValue(request)
                    .retrieve()
                    // 5xx 에러 (503 포함) 발생 시 재시도 로직 적용
                    .onStatus(status -> status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Gemini API Error: Status " + clientResponse.statusCode() + " Body: " + body)))
                    .bodyToMono(GeminiResponse.class)
                    // 3초 대기 후 최대 3번 재시도 (503 오류 대비)
                    .retryWhen(reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(3))
                            .filter(throwable -> throwable instanceof RuntimeException
                                    && throwable.getMessage().contains("503 SERVICE_UNAVAILABLE")))
                    .block();

            // 4. 응답 파싱 및 JSON 텍스트 반환
            if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                return response.getCandidates().get(0).getContent().getParts().get(0).getText();
            }
            return "{\"score\":0, \"feedback\":\"AI 응답을 받지 못했습니다.\"}";

        } catch (Exception e) {
            System.err.println("Gemini API 호출 실패 (재시도 후): " + e.getMessage());
            return "{\"score\":0, \"feedback\":\"API 통신 중 심각한 오류 발생 (재시도 3회 실패): " + e.getMessage().replace("\"", "'") + "\"}";
        }
    }
}