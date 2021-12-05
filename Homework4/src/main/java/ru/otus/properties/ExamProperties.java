package ru.otus.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "exam")
@Component
@Data
public class ExamProperties {

    private int targetScore;
    private Locale locale;
}
