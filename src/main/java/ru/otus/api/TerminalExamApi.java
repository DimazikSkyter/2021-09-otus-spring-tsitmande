package ru.otus.api;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class TerminalExamApi implements ExamApi {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getResponse() {
        return scanner.nextLine();
    }
}
