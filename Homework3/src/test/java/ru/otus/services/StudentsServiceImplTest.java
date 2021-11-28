package ru.otus.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.exception.StudentAlreadyExists;
import ru.otus.exception.StudentNotFound;
import ru.otus.properties.ExamProperties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentsServiceImplTest {

    @Mock
    private MessageSource messageSource;
    @Mock
    private ExamProperties examProperties;

    @InjectMocks
    private StudentsServiceImpl studentsService;

    @Test
    void registerNewStudent() {
        String studentName = "student 1";
        studentsService.registerNewStudent(studentName);
        assertTrue(studentsService.getAllStudents().containsKey(studentName));
    }

    @Test
    void registerNewStudentAlreadyExists() {
        String studentName = "student 2";
        studentsService.registerNewStudent(studentName);
        assertThrows(StudentAlreadyExists.class, () -> studentsService.registerNewStudent(studentName));
    }

    @Test
    void updateStudentResult() {
        String studentName = "student 3";
        studentsService.registerNewStudent(studentName);
        studentsService.updateStudentResult(studentName, false);
        assertFalse(studentsService.getAllStudents().get(studentName));
    }

    @Test
    void updateStudentResultStudentNotFound() {
        String studentName = "student 3";
        assertThrows(StudentNotFound.class, () -> studentsService.updateStudentResult(studentName, false));
    }
}