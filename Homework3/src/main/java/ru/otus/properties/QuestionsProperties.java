package ru.otus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "questions")
@Component
@Data
public class QuestionsProperties {

    private String path;
}
