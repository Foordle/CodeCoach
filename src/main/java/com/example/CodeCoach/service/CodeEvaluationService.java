package com.example.CodeCoach.service;

import com.example.CodeCoach.domain.EvaluationRequest;
import com.example.CodeCoach.domain.EvaluationResult;
import com.example.CodeCoach.repository.AiEvaluationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

/**
 * 코드 평가 요청을 처리하고 AI 피드백을 통합하는 서비스 클래스.
 */
@Service
public class CodeEvaluationService {

    private final AiEvaluationRepository aiEvaluationRepository;
    private final ObjectMapper objectMapper;

    public CodeEvaluationService(AiEvaluationRepository aiEvaluationRepository, ObjectMapper objectMapper) {
        this.aiEvaluationRepository = aiEvaluationRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * 코드 평가 요청을 처리하고 평가 결과를 반환합니다.
     * @param request 평가 요청 데이터
     * @return 평가 결과
     */
    public EvaluationResult evaluateCode(EvaluationRequest request) {

        //1. 모델 결정: 사용자가 View에서 선택한 모델을 사용하며, 선택되지 않은 경우 flash를 기본값으로 설정
        String modelName = request.getModelName();
        if (modelName == null || modelName.isEmpty()) {
            modelName = "gemini-2.5-flash"; // 기본값 설정
        }

        // 2. AI에게 전달할 프롬프트 구성
        String prompt = buildAiPrompt(request);

        String aiJsonFeedback = "";
        int score = 0;
        String detailedFeedback = "AI 평가를 기다리는 중..."; // 초기 피드백 메시지

        try {
            // 3. Repository를 통해 실제 AI 피드백 요청 (모델 이름, 온도, Top-P 전달)
            aiJsonFeedback = aiEvaluationRepository.requestAiFeedback(
                    modelName,
                    request.getTemperature(),
                    request.getTopP(),
                    prompt
            );

            // 4. JSON 응답 파싱
            JsonNode rootNode = objectMapper.readTree(aiJsonFeedback);
            // score 필드 파싱 (기본값 0)
            score = rootNode.path("score").asInt(0);
            // feedback 필드 파싱 (기본값: 파싱 실패 피드백)
            detailedFeedback = rootNode.path("feedback").asText("JSON 응답 구조에 오류가 발생했습니다. AI 모델의 응답을 확인해주세요.");

        } catch (Exception e) {
            System.err.println("JSON 파싱 오류 또는 API 요청 실패: " + e.getMessage());
            detailedFeedback = "JSON 응답 파싱 실패 또는 API 요청 중 심각한 오류가 발생했습니다: " + e.getMessage();
            // 요청 실패 시 서버 연결 오류 메시지를 포함
            return new EvaluationResult(
                    "AI_FAILURE",
                    "서버 연결 오류 또는 처리 실패: " + e.getMessage(),
                    0,
                    detailedFeedback
            );
        }

        // 5. 결과 반환
        return new EvaluationResult(
                "AI_SUCCESS",
                "컴파일러를 사용하지 않고 AI 평가만 진행됨. 사용 모델: " + modelName,
                score,
                detailedFeedback
        );
    }

    /**
     * AI에게 전달할 프롬프트를 구성합니다.
     * @param request 평가 요청 데이터
     * @return 완성된 프롬프트 문자열
     */
    private String buildAiPrompt(EvaluationRequest request) {
        StringBuilder sb = new StringBuilder();
        // ... (이전과 동일한 프롬프트 구성 로직)
        sb.append("당신은 'CodeCoach AI 평가 시스템'의 전문 평가 모델입니다. 사용자가 제출한 코드를 평가 목적('")
                .append(request.getEvaluationPurpose()).append("')과 아키텍처 패턴('")
                .append(request.getArchitecturePattern()).append("')을 기준으로 평가하고, 점수(0-100점)와 상세 피드백을 JSON 형식으로 제공해주세요.\n\n")
                .append("## 평가 항목\n")
                .append("1. **일반 피드백:** 코드 구조, 가독성, 변수 이름 등에 대한 간결한 피드백\n")
                .append("2. **상세 분석:** 평가 목적과 아키텍처 패턴 준수 여부에 대한 심층 분석 (가장 중요한 부분)\n\n")
                .append("## 출력 형식 (JSON Schema)\n")
                .append("{\n")
                .append("  \"score\": <정수 0-100>,\n")
                .append("  \"feedback\": <Markdown 형식의 상세 피드백 문자열>\n")
                .append("}\n\n")
                .append("## 평가 요청 정보\n")
                .append(" - 언어: ").append(request.getLanguage()).append("\n")
                .append(" - 평가 목적: ").append(request.getEvaluationPurpose()).append("\n")
                .append(" - 아키텍처 패턴: ").append(request.getArchitecturePattern()).append("\n\n")
                .append("## 제출 코드\n")
                .append("```").append(request.getLanguage()).append("\n")
                .append(request.getCodeContent()).append("\n")
                .append("```\n\n")
                .append("제출 코드에 대한 평가를 JSON 형식으로 반환하세요. 피드백 내용은 최소 3줄 이상 Markdown으로 작성해 주세요.");

        return sb.toString();
    }

    // 📢 getModelByPurpose 메서드가 제거되었습니다.
}