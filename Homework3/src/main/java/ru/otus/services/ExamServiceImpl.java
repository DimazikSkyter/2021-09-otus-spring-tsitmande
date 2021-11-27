package ru.otus.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.api.ExamApi;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.properties.ExamProperties;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamServiceImpl implements ExamService {

    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final ExamApi examApi;
    private final ExamProperties examProperties;


    public int startNewExam() throws FileNotFoundException {
        int score = 0;
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            score += tellQuestionAndWaitResponseAndHandleIt(question);
        }
        return score;
    }

    private int tellQuestionAndWaitResponseAndHandleIt(Question question) {
        System.out.println(question.getQuestion() + "\n" + question.getAnswer().toString());
        String response = examApi.getResponse();
        if (check(question.getAnswer(), response)) {
            log.info(messageSource.getMessage("exam.write.answer", null, examProperties.getLocale()));
            return 1;
        }
        log.info(messageSource.getMessage("exam.wrong.answer", new String[]{question.getAnswer().getRealAnswer()}, examProperties.getLocale()));
        return 0;
    }

    private boolean check(Answer answer, String response) {
        return answer.getRealAnswer().equals(response);
    }
}
