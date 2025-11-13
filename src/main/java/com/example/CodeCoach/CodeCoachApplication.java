package com.example.CodeCoach;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CodeCoachApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeCoachApplication.class, args);
    }

    // WebClient.Builder를 Bean으로 등록
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // WebClient 인스턴스를 Bean으로 등록 (AiEvaluationRepository가 주입받을 대상)
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}