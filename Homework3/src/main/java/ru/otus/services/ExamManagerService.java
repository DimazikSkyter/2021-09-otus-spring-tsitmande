package ru.otus.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import ru.otus.api.ExamApi;
import ru.otus.properties.ExamProperties;

import java.io.FileNotFoundException;


@Service
@Slf4j
@RequiredArgsConstructor
public class ExamManagerService implements ManagerService, ApplicationListener<ContextRefreshedEvent> {

    private final MessageSource messageSource;
    private final ExamService examService;
    private final ExamProperties examProperties;
    private final ExamApi examApi;
    private final StudentsService studentsService;

    @Override
    public void start() {
        while (true) {
            log.info(messageSource.getMessage("exam.hello", null, examProperties.getLocale()));
            String response = examApi.getResponse();
            switch (response) {
                case "1":
                    startNewExam();
                    break;
                case "2":
                    printTotalStudentsGrades();
                    break;
                default:
            }
        }
    }

    private void startNewExam() {
        try {
            String fio = askFio();
            studentsService.registerNewStudent(fio);
            val score = examService.startNewExam();
            boolean isPassed = score >= examProperties.getTargetScore();
            log.info(messageSource.getMessage("exam.total.score", new Object[]{fio, score, examResult(isPassed)}, examProperties.getLocale()) + "\n\n");
            studentsService.updateStudentResult(fio, isPassed);
        } catch (FileNotFoundException e) {
            log.info(messageSource.getMessage("error.failed.to.find.questions", null, examProperties.getLocale()));
        }
    }

    private String examResult(boolean isPassed) {
        return isPassed
                ? messageSource.getMessage("exam.total.pass", null, examProperties.getLocale())
                : messageSource.getMessage("exam.total.fail", null, examProperties.getLocale());
    }


    private String askFio() {
        log.info(messageSource.getMessage("exam.print.your.name", null, examProperties.getLocale()));
        return examApi.getResponse();
    }

    private void printTotalStudentsGrades() {
        log.info(messageSource.getMessage("exam.summary.total.score", null, examProperties.getLocale()));
        studentsService.getAllStudents().forEach(
                (studentFio, isPassed) ->
                        log.info(messageSource.getMessage(
                                "exam.student.result",
                                new Object[]{studentFio, isPassed},
                                examProperties.getLocale()))
        );
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        start();
    }
}
