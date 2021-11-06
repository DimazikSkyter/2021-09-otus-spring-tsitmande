package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.services.ExamService;


@ComponentScan
public class ExamApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExamApp.class);

        ExamService examService = context.getBean(ExamService.class);
        examService.start();
    }
}
