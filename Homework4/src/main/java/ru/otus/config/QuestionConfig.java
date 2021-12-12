package ru.otus.config;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.properties.ExamProperties;
import ru.otus.properties.QuestionsProperties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class QuestionConfig {

    @Bean
    public List<Question> questions(MessageSource messageSource, ExamProperties examProperties, QuestionsProperties questionsProperties) {
        CSVReaderBuilder csvReaderBuilder = csvReaderBuilder(questionsProperties.getPath());
        return handleQuestionsFromCsv(csvReaderBuilder, messageSource, examProperties);
    }

    private List<Question> handleQuestionsFromCsv(CSVReaderBuilder csvReaderBuilder, MessageSource messageSource, ExamProperties examProperties) {
        try (CSVReader reader = csvReaderBuilder.build()) {
            List<String[]> rows = reader.readAll();
            return rows.stream().map(this::arrayToQuestion).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException(messageSource.getMessage("error.get.ex.while.creating.questions", null, examProperties.getLocale()), e);
        }
    }

    private Question arrayToQuestion(String[] array) {
        String question = array[0];
        Answer answer = arrayToAnswer(array);
        return new Question(question, answer);
    }

    private Answer arrayToAnswer(String[] array) {
        List<String> variants = Arrays.stream(array).skip(1).limit(4).collect(Collectors.toList());
        return new Answer(variants, array[5]);
    }

    private CSVReaderBuilder csvReaderBuilder(String path) {
        try {
            return new CSVReaderBuilder(
                    new FileReader(
                            this.getClass().getClassLoader().getResource(path).getPath()
                    ))
                    .withSkipLines(1)
                    .withCSVParser(
                            new CSVParserBuilder().withSeparator(';').build()
                    );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
