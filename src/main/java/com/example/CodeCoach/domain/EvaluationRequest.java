package com.example.CodeCoach.domain;

public class EvaluationRequest {
    private String codeContent;
    private String architecturePattern;
    private String evaluationPurpose;
    private String language;
    private Float temperature;
    private Float topP;
    private String modelName;

    public EvaluationRequest() {}

    // Getter, Setter
    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public String getArchitecturePattern() {
        return architecturePattern;
    }

    public void setArchitecturePattern(String architecturePattern) {
        this.architecturePattern = architecturePattern;
    }

    public String getEvaluationPurpose() {
        return evaluationPurpose;
    }

    public void setEvaluationPurpose(String evaluationPurpose) {
        this.evaluationPurpose = evaluationPurpose;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getTemperature() {
        return temperature;
    }
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTopP() {
        return topP;
    }
    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
