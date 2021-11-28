package ru.otus.dao;

import ru.otus.domain.Question;

import java.util.List;

public class QuestionsDaoCsv implements QuestionsDao {

    private final List<Question> questions;

    public QuestionsDaoCsv(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}