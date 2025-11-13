package com.example.CodeCoach.service;
import com.example.CodeCoach.domain.EvaluationRequest;
import com.example.CodeCoach.domain.EvaluationResult;
import com.example.CodeCoach.repository.AiEvaluationRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeEvaluationService {

    private final AiEvaluationRepository aiEvaluationRepository;

    public CodeEvaluationService(AiEvaluationRepository aiEvaluationRepository) {
        this.aiEvaluationRepository = aiEvaluationRepository;
    }

    public EvaluationResult evaluateCode(EvaluationRequest request) {
        // Docker 실행 로직 여기에 (일단 생략)

        // 1. AI에게 전달할 프롬프트 구성
        String prompt = buildAiPrompt(request);

        // 2. Repository를 통해 AI 피드백 요청 (Dummy)
        String aiFeedback = aiEvaluationRepository.requestAiFeedback(prompt);

        // 3. 점수 생성 (Dummy)
        Random random = new Random();
        int score = 70 + random.nextInt(30);

        // 4. 결과 반환
        return new EvaluationResult(
                "AI_ONLY", // 상태: 컴파일러 없이 AI 평가만 진행됨
                "컴파일러를 사용하지 않고 AI 평가만 진행됨.", // 실행 결과 더미
                score,
                aiFeedback
        );
    }

    /**
     * AI 모델에게 전달할 프롬프트를 구성하는 헬퍼 메서드
     */
    private String buildAiPrompt(EvaluationRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("[System Instruction]\n");
        sb.append("당신은 ").append(request.getLanguage()).append(" 코드의 아키텍처 및 품질 전문가입니다. 엄격하게 분석하고 피드백을 제공하세요.\n\n");

        sb.append("[평가 요청 정보]\n");
        sb.append("언어: ").append(request.getLanguage()).append("\n");
        sb.append("아키텍처 패턴: ").append(request.getArchitecturePattern()).append("\n");
        sb.append("평가 목적: ").append(request.getEvaluationPurpose()).append("\n\n");

        sb.append("[분석할 코드]\n");
        sb.append("```").append(request.getLanguage().toLowerCase()).append("\n");
        sb.append(request.getCodeContent()).append("\n");
        sb.append("```");

        return sb.toString();
    }
}