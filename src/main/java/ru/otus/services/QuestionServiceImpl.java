package ru.otus.services;

import ru.otus.dao.QuestionsDao;
import ru.otus.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionsDao questionsDao;

    public QuestionServiceImpl(QuestionsDao questionsDao) {
        this.questionsDao = questionsDao;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionsDao.getQuestions();
    }
}
