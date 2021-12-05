package ru.otus.config;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.properties.QuestionsProperties;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Configuration
public class ExamAppConfig {

    @Bean
    @Scope("prototype")
    CSVReaderBuilder csvReaderBuilder(QuestionsProperties questionsProperties) {
        return csvReaderBuilder(questionsProperties.getPath());
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
