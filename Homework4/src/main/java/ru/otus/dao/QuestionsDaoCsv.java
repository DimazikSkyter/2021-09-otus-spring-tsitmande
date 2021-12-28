package ru.otus.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.otus.domain.Question;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionsDaoCsv implements QuestionsDao {

    @Getter
    private final List<Question> questions;
}