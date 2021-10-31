package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.api.ExamApi;
import ru.otus.services.ExamService;


@ComponentScan
public class ExamApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExamApp.class);

        ExamService examService = context.getBean(ExamService.class);
        ExamApi examApi = context.getBean(ExamApi.class);

        while(true) {
            System.out.println("If you want to start exam write 1, to get students exam status write 2, to exit 0");
            String response = examApi.getResponse();
            switch (response) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    examService.startNewExam();
                    break;
                case "2":
                    examService.printTotalStudentsGrades();
                    break;
                default:
            }
        }
    }
}
