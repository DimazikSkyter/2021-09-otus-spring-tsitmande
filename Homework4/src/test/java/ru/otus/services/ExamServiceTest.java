package ru.otus.services;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.api.ExamApi;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.properties.ExamProperties;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private ExamApi examApi;
    @Mock
    private ExamProperties examProperties;

    @InjectMocks
    private ExamServiceImpl examService;

    @BeforeEach
    void init () throws FileNotFoundException {
        List<Question> questions = new ArrayList<>();
        Answer answer = new Answer(Arrays.asList("a", "b", "c", "d"), "a");
        Answer answer2 = new Answer(Arrays.asList("a", "b", "c", "d"), "b");
        Question question = new Question("Test question", answer);
        Question question2 = new Question("Test question", answer2);
        questions.add(question);
        questions.add(question2);
        Mockito.doReturn(questions).when(questionService).getAllQuestions();

        Mockito.when(examApi.getResponse(Mockito.anyString())).thenReturn("a").thenReturn("b");
    }

    @Test
    void startNewExam() throws FileNotFoundException {
        val score = examService.startNewExam();
        Assertions.assertThat(score).isEqualTo(2);
    }
}