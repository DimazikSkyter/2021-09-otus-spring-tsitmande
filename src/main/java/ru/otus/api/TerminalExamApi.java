package ru.otus.api;

import java.util.Scanner;

public class TerminalExamApi implements ExamApi {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getResponse() {
        return scanner.nextLine();
    }
}
