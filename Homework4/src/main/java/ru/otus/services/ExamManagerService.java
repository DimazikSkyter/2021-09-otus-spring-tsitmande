package ru.otus.services;

import java.util.List;

public interface ExamManagerService {

    void startNewExam();

    List<String> currentStudentsResult();

    boolean canStartNewExam();

    boolean canCheckStatus();
}
