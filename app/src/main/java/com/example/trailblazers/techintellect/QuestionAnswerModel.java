package com.example.trailblazers.techintellect;

public class QuestionAnswerModel {
    private String Question;
    private String Answer;

    public QuestionAnswerModel(String question, String answer) {
        Question = question;
        Answer = answer;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
