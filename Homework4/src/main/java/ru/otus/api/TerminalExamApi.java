package ru.otus.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Slf4j
public class TerminalExamApi implements ExamApi {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getResponse(String question) {
        log.info(question);
        return scanner.nextLine();
    }
}