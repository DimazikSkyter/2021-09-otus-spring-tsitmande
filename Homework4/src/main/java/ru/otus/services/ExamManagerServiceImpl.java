package ru.otus.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.properties.ExamProperties;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ExamManagerServiceImpl implements ExamManagerService {

    private final MessageSource messageSource;
    private final ExamService examService;
    private final ExamProperties examProperties;
    private final StudentsService studentsService;

    @Override
    public void startNewExam() {
        try {
            val score = examService.startNewExam();
            boolean isPassed = score >= examProperties.getTargetScore();
            log.info(messageSource.getMessage("exam.total.score",
                    new Object[]{studentsService.getCurrentStudent(), score, examResult(isPassed)},
                    examProperties.getLocale()) + "\n\n");
            studentsService.updateStudentResult(isPassed);
        } catch (Exception e) {
            log.info(messageSource.getMessage("error.failed.to.find.questions", null, examProperties.getLocale()));
        }
    }

    public boolean canStartNewExam() {
        if (studentsService.getCurrentStudent() == null) {
            log.info(messageSource.getMessage("not.login", null, examProperties.getLocale()));
            return false;
        }
        if (studentsService.isExamFinishedForCurrentStudent()) {
            log.info("{}, вы уже сдали экзамен.", studentsService.getCurrentStudent());
            return false;
        }
        return true;
    }

    @Override
    public boolean canCheckStatus() {
        if (studentsService.getCurrentStudent() == null) {
            log.info(messageSource.getMessage("not.login", null, examProperties.getLocale()));
            return false;
        }
        if (!studentsService.isExamFinishedForCurrentStudent()) {
            log.info("{}, вы пока не сдали экзамен.", studentsService.getCurrentStudent());
            return false;
        }
        return true;
    }

    private String examResult(boolean isPassed) {
        return isPassed
                ? messageSource.getMessage("exam.total.pass", null, examProperties.getLocale())
                : messageSource.getMessage("exam.total.fail", null, examProperties.getLocale());
    }


    @Override
    public List<String> currentStudentsResult() {
        return studentsService.getAllStudents().entrySet().stream().map(
                entry ->
                        messageSource.getMessage(
                                "exam.student.result",
                                new Object[]{entry.getKey(), entry.getValue()},
                                examProperties.getLocale())
        ).collect(Collectors.toList());
    }
}
