package ru.otus.api;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TerminalExamApi implements ExamApi {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getResponse() {
        return scanner.nextLine();
    }
}
