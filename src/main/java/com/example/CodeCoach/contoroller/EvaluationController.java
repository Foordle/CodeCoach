package com.example.CodeCoach.contoroller;

import com.example.CodeCoach.domain.EvaluationRequest;
import com.example.CodeCoach.domain.EvaluationResult;
import com.example.CodeCoach.service.CodeEvaluationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    private final CodeEvaluationService evaluationService;

    public EvaluationController(CodeEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * POST 요청을 받아 코드 평가를 요청합니다.
     */
    @PostMapping
    public EvaluationResult evaluateCode(@RequestBody EvaluationRequest request) {

        // 1. 입력 데이터 유효성 검사
        if (request.getCodeContent() == null || request.getCodeContent().trim().isEmpty()) {
            return new EvaluationResult("INVALID_INPUT", "평가할 코드 내용을 입력해야 합니다.", 0, "코드를 입력한 후 다시 요청해주세요.");
        }

        // 2. Service 계층 호출
        System.out.println("Controller: 평가 요청 시작 -> 언어: " + request.getLanguage());
        EvaluationResult result = evaluationService.evaluateCode(request);

        // 3. 결과 반환
        return result;
    }
}