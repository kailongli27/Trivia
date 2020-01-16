package com.example.trivia.model;

import androidx.annotation.NonNull;

public class Question {

    private String answer;
    private boolean answerIsTrue;

    public Question(){

    }

    public Question(String answer, boolean answerIsTrue) {
        this.answer = answer;
        this.answerIsTrue = answerIsTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return answerIsTrue;
    }

    public void setAnswerIsTrue(boolean answerIsTrue) {
        this.answerIsTrue = answerIsTrue;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" + "answer='" + answer + '\'' + ", answerIsTrue=" + answerIsTrue + '}';
    }
}
