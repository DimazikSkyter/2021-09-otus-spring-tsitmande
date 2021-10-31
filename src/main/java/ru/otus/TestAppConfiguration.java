package ru.otus;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.api.ExamApi;
import ru.otus.api.TerminalExamApi;
import ru.otus.dao.QuestionsDao;
import ru.otus.dao.QuestionsDaoCsv;
import ru.otus.domain.Question;
import ru.otus.services.ExamService;
import ru.otus.services.NoRetryExamService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:configuration.properties")
public class TestAppConfiguration {

    @Bean
    public QuestionsDao questionsDao(@Value("${ru.otus.questions.path}") String path) throws FileNotFoundException {
        CSVReaderBuilder csvReaderBuilder = csvReaderBuilder(path);
        List<Question> questions = questions(csvReaderBuilder);
        return new QuestionsDaoCsv(questions);
    }

    @Bean
    ExamApi examApi() {
        return new TerminalExamApi();
    }

    @Bean
    ExamService examService(QuestionsDao questionsDao, ExamApi examApi, @Value("${ru.otus.targetScore}") int targetScore) {
        return new NoRetryExamService(questionsDao, examApi, targetScore);
    }

    private List<Question> questions(CSVReaderBuilder csvReaderBuilder) {
        try (CSVReader reader = csvReaderBuilder.build()) {
            List<String[]> rows = reader.readAll();
            return rows.stream().map(Question::new).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Get ex while creating questions", e);
        }
    }

    private  CSVReaderBuilder csvReaderBuilder(String path) throws FileNotFoundException {
        return new CSVReaderBuilder(
                new FileReader(
                        this.getClass().getClassLoader().getResource(path).getPath()
                ))
                .withSkipLines(1)
                .withCSVParser(
                        new CSVParserBuilder().withSeparator(';').build()
                );
    }
}
