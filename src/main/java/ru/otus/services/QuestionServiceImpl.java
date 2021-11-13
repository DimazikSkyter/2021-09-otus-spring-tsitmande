package ru.otus.services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionsDao;
import ru.otus.dao.QuestionsDaoCsv;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    @Value("${ru.otus.questions.path}")
    private String path;
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
            return rows.stream().map(this::arrayToQuestion).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Get ex while creating questions", e);
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
