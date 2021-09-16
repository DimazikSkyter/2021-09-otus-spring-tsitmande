package ru.otus.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Question {

    public Question(String[] questionAndAnswers) {
        this.question = questionAndAnswers[0];
        List<String> answers = Arrays.stream(questionAndAnswers).skip(1).collect(Collectors.toList());
        this.answer = new Answer(answers);
    }

    private final String question;
    private final Answer answer;

    @Override
    public String toString() {
        return String.format("%s\n%s", question, answer);
    }
}
