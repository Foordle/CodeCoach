package com.example.CodeCoach.repository.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

// Gemini API로부터 받을 응답 JSON 구조
@Getter
@Setter
public class GeminiResponse {
    private List<Candidate> candidates;

    @Getter
    @Setter
    public static class Candidate {
        private Content content;
    }

    @Getter
    @Setter
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    public static class Part {
        private String text;
    }
}