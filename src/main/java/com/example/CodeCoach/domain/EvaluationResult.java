package com.example.CodeCoach.domain;

public class EvaluationResult {
    private String compileStatus;
    private String runOutput;
    private int score;
    private String aiFeedback;

    public EvaluationResult(String compileStatus, String runOutput, int score, String aiFeedback) {
        this.compileStatus = compileStatus;
        this.runOutput = runOutput;
        this.score = score;
        this.aiFeedback = aiFeedback;
    }

    // Getter
    public String getCompileStatus() {
        return compileStatus;
    }

    public String getRunOutput() {
        return runOutput;
    }

    public int getScore() {
        return score;
    }

    public String getAiFeedback() {
        return aiFeedback;
    }
}

