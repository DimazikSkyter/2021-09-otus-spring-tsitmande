package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.services.ExamService;


@ComponentScan
@PropertySource("classpath:configuration.properties")
public class ExamApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExamApp.class);

        ExamService examService = context.getBean(ExamService.class);
        examService.start();
    }
}
