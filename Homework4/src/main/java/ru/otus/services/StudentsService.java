package ru.otus.services;

import java.util.Map;

public interface StudentsService {

    void registerNewStudent(String name);

    void updateStudentResult(boolean result);

    Map<String, Boolean> getAllStudents();

    boolean isExamFinishedForCurrentStudent();

    String getCurrentStudent();
}
