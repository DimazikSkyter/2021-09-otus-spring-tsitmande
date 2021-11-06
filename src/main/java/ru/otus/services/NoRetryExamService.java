package ru.otus.services;

import lombok.RequiredArgsConstructor;
import ru.otus.api.ExamApi;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NoRetryExamService implements ExamService {

    private final QuestionService questionService;
    private final ExamApi examApi;
    private final int targetScore;
    private final Map<String, Boolean> students = new HashMap<>();

    private void printTotalStudentsGrades() {
        System.out.println("Total results of exams are:");
        students.forEach((studentFio, isPassed) ->
                System.out.println("Student: " + studentFio + ", is passed " + isPassed)
        );
    }

    @Override
    public void start() {
        while(true) {
            System.out.println("If you want to start exam write 1, to get students exam status write 2, to exit 0");
            String response = examApi.getResponse();
            switch (response) {
                case "0":
                    System.exit(0);
                    break;
                case "1":
                    try {
                        startNewExam();
                    } catch (FileNotFoundException e) {
                        System.out.println("Failed to start new exam. The questions not found.");
                    }
                    break;
                case "2":
                    printTotalStudentsGrades();
                    break;
                default:
            }
        }
    }

    private void startNewExam() throws FileNotFoundException {
        String fio = askFio();
        int score = 0;
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            score += tellQuestionAndWaitResponseAndHandleIt(question);
        }
        System.out.println(fio + " your total score is " + score + ". And you " + (score >= targetScore ? " successed " : " failed ") + " exam.\n\n" );
        students.put(fio, score >= targetScore);
    }

    private int tellQuestionAndWaitResponseAndHandleIt(Question question) {
        System.out.println(question.getQuestion() + "\n" + question.getAnswer().toString());
        String response = examApi.getResponse();
        if (check(question.getAnswer(), response)) {
            System.out.println("What's right!");
            return 1;
        }
        System.out.println("Wrong answer. Correct answer is " + question.getAnswer().getRealAnswer());
        return 0;
    }

    private boolean check(Answer answer, String response) {
        return answer.getRealAnswer().equals(response);
    }

    private String askFio() {
        System.out.println("Print your name and surname");
        return examApi.getResponse();
    }
}
