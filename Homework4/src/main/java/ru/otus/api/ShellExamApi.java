package ru.otus.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.properties.ExamProperties;
import ru.otus.services.ExamManagerService;
import ru.otus.services.StudentsService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellExamApi {

    private final ExamManagerService examManagerService;
    private final StudentsService studentsService;
    private final MessageSource messageSource;
    private final ExamProperties examProperties;

    @ShellMethod("Логин под пользователем")
    public String login(@ShellOption String login) {
        studentsService.registerNewStudent(login);
        return messageSource.getMessage("say.hello", new String[]{login}, examProperties.getLocale());
    }

    @ShellMethod("Начало нового экзамена для зарегистрированного студента")
    @ShellMethodAvailability(value = "canStartNewExam")
    public String startNewExam() {
        examManagerService.startNewExam();
        return messageSource.getMessage("exam.finish", new String[]{}, examProperties.getLocale());
    }


    @ShellMethod("Текущий протокол экзамена")
    public List<String> currentStatus() {
        log.info(messageSource.getMessage("exam.summary.total.score", null, examProperties.getLocale()));
        return examManagerService.currentStudentsResult();
    }

    private Availability canStartNewExam() {
        return examManagerService.canStartNewExam() ? Availability.available() : Availability.unavailable("");
    }
}
