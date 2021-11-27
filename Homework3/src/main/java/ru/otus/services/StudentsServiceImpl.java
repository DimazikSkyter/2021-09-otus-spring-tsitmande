package ru.otus.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.exception.StudentAlreadyExists;
import ru.otus.exception.StudentNotFound;
import ru.otus.properties.ExamProperties;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentsServiceImpl implements StudentsService {

    private final Map<String, Boolean> students = new HashMap<>();
    private final MessageSource messageSource;
    private final ExamProperties examProperties;

    @Override
    public void registerNewStudent(String name) {
        if(students.containsKey(name)) {
            throw new StudentAlreadyExists(
                    messageSource.getMessage("student.already.exists", new String[]{name}, examProperties.getLocale()));
        }
        students.put(name, null);
    }

    @Override
    public void updateStudentResult(String name, boolean result) {
        if(!students.containsKey(name)) {
            throw new StudentNotFound(
                    messageSource.getMessage("student.didnt.find", new String[] {name}, examProperties.getLocale()));
        }
        students.replace(name, result);
    }

    @Override
    public Map<String, Boolean> getAllStudents() {
        return students;
    }
}
