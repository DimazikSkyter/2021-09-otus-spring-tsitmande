package ru.otus.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.otus.domain.Question;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionsDaoCsv implements QuestionsDao {

    private List<Question> questions;

    public QuestionsDaoCsv() throws FileNotFoundException {
        CSVReaderBuilder csvReaderBuilder = createBuilder();
        try (CSVReader reader = csvReaderBuilder.build()) {
            List<String[]> rows = reader.readAll();
            this.questions = rows.stream().map(Question::new).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private CSVReaderBuilder createBuilder() throws FileNotFoundException {
        return new CSVReaderBuilder(
                new FileReader(
                        this.getClass().getClassLoader().getResource("ru/otus/questions.csv").getPath()
                ))
                .withSkipLines(1)
                .withCSVParser(
                        new CSVParserBuilder().withSeparator(';').build()
                );
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }
}
