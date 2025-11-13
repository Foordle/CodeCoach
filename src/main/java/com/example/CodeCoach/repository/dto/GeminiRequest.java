package com.example.CodeCoach.repository.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.Map;

// Gemini API에 보낼 요청 JSON 구조
@Getter
@Builder
public class GeminiRequest {
    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Builder
    public static class Content {
        private String role;
        private List<Part> parts;
    }

    @Getter
    @Builder
    public static class Part {
        private String text;
    }

    @Getter
    @Builder
    public static class GenerationConfig {
        private String responseMimeType;
        private ResponseSchema responseSchema;
        private Float temperature;
        private Float topP;
    }

    @Getter
    @Builder
    public static class ResponseSchema {
        private String type;
        private Map<String, Property> properties;
        private List<String> required;
    }

    @Getter
    @Builder
    public static class Property {
        private String type;
        private String description;
    }
}