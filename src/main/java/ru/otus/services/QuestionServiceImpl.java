package ru.otus.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionsDao;
import ru.otus.dao.QuestionsDaoCsv;
import ru.otus.domain.Question;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final String path;
    private QuestionsDao questionsDao;

    @Override
    public List<Question> getAllQuestions() throws FileNotFoundException {
        if (questionsDao == null) {
            questionsDao = questionsDao();
        }
        return questionsDao.getQuestions();
    }

    private QuestionsDao questionsDao() throws FileNotFoundException {
        CSVReaderBuilder csvReaderBuilder = csvReaderBuilder(path);
        List<Question> questions = questions(csvReaderBuilder);
        return new QuestionsDaoCsv(questions);
    }

    private List<Question> questions(CSVReaderBuilder csvReaderBuilder) {
        try (CSVReader reader = csvReaderBuilder.build()) {
            List<String[]> rows = reader.readAll();
            return rows.stream().map(Question::new).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Get ex while creating questions", e);
        }
    }

    private CSVReaderBuilder csvReaderBuilder(String path) throws FileNotFoundException {
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
