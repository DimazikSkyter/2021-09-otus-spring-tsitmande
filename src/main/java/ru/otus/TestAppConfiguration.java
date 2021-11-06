package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.api.ExamApi;
import ru.otus.services.ExamService;
import ru.otus.services.NoRetryExamService;
import ru.otus.services.QuestionService;
import ru.otus.services.QuestionServiceImpl;

@Configuration
@PropertySource("classpath:configuration.properties")
public class TestAppConfiguration {

    @Bean
    public QuestionService questionService(@Value("${ru.otus.questions.path}") String path) {
        return new QuestionServiceImpl(path);
    }

    @Bean
    ExamService examService(QuestionService questionService, ExamApi examApi, @Value("${ru.otus.targetScore}") int targetScore) {
        return new NoRetryExamService(questionService, examApi, targetScore);
    }
}
