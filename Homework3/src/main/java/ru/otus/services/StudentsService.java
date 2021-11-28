package ru.otus.services;

import java.util.Map;

public interface StudentsService {

    void registerNewStudent(String name);

    void updateStudentResult(String name, boolean result);

    Map<String, Boolean> getAllStudents();
}
