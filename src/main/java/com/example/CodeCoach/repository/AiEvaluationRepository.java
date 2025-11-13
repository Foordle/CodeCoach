package com.example.CodeCoach.repository;

public class AiEvaluationRepository {
    /**
     * AI 모델에게 코드 평가를 요청하고 피드백을 받습니다. (현재 더미)
     * @param fullPrompt AI에게 전달할 전체 프롬프트
     * @return AI가 생성한 평가 피드백 텍스트
     */
    public String requestAiFeedback(String fullPrompt) {
        // 🚨 현재는 AI API 호출 대신 더미 피드백을 반환합니다.

        try {
            // 네트워크 지연 시뮬레이션
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Dummy Feedback Generation
        return String.format(
                "요청된 기준 (코드 길이: %d, 목적: %s)에 따른 코드 전문가의 피드백입니다.\n\n" +
                        "✅ 일반 피드백:\n" +
                        "   - 코드의 구조가 명확하고 변수 이름이 직관적입니다.\n" +
                        "💡 심화 분석:\n" +
                        "   - 아키텍처 패턴 (%s) 준수 여부를 판단하려면, 더 큰 모듈 구조가 필요합니다.\n" +
                        "   - '유지보수성' 관점에서, 매직 넘버(10, 20) 대신 상수를 사용하는 것이 권장됩니다.",
                fullPrompt.length(),
                fullPrompt.contains("성능") ? "성능 최적화" : "유지보수 및 확장성",
                fullPrompt.contains("MVC") ? "MVC 패턴" : "계층형 아키텍처"
        );
    }
}
