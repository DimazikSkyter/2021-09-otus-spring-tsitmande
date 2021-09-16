package ru.otus.dao;

import ru.otus.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionsDao {

    List<Question> getQuestions();
}
