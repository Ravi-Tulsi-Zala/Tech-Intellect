package com.example.trailblazers.techintellect;

public class QuestionAnswerModel {
    private String Question;
    private String Correct_answer;
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;


    public QuestionAnswerModel() {
    }
    public QuestionAnswerModel(String question, String correct_answer){
        Question = question;
        Correct_answer = correct_answer;
    }
    public QuestionAnswerModel(String question, String answer, String answer1,String answer2,String answer3,String answer4) {
        Question = question;
        Correct_answer = answer;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;

    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Correct_answer;
    }

    public void setAnswer(String answer) {
        Correct_answer = answer;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getCorrect_answer() {
        return Correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        Correct_answer = correct_answer;
    }

    public String getAnswer2() {
        return Answer2;

    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }
}
