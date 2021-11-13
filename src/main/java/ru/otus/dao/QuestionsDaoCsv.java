package ru.otus.dao;

import ru.otus.domain.Question;

import java.io.FileNotFoundException;
import java.util.List;

public class QuestionsDaoCsv implements QuestionsDao {

    private final List<Question> questions;

    public QuestionsDaoCsv(List<Question> questions) throws FileNotFoundException {
        this.questions = questions;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
