package ru.otus.domain;

import lombok.Getter;

@Getter
public class Question {

    private final String question;
    private final Answer answer;

    public Question(String question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", question, answer);
    }
}
