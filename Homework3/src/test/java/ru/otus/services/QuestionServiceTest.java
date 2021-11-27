package ru.otus.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Question;
import ru.otus.properties.ExamProperties;
import ru.otus.properties.QuestionsProperties;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableConfigurationProperties
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Configuration
    static class TestConfiguration {

        @MockBean
        MessageSource messageSource;

        @Bean
        public ExamProperties examProperties() {
            return new ExamProperties();
        }

        @Bean
        public QuestionsProperties questionsProperties() {
            return new QuestionsProperties();
        }

        @Bean
        public QuestionService questionService(MessageSource messageSource, ExamProperties examProperties, QuestionsProperties questionsProperties) {
            return new QuestionServiceImpl(messageSource, examProperties, questionsProperties);
        }

    }

    @Test
    void getAllQuestions() throws FileNotFoundException {
        List<Question> allQuestions = questionService.getAllQuestions();

        assertEquals(5, allQuestions.size());
        for (Question question : allQuestions) {
            assertEquals(4, question.getAnswer().getVariants().size());
        }
    }

}
