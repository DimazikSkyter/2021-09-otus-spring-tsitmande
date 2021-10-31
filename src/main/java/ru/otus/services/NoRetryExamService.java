package ru.otus.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.api.ExamApi;
import ru.otus.dao.QuestionsDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NoRetryExamService implements ExamService {

    private final QuestionsDao questionsDao;
    private final ExamApi examApi;
    private final int targetScore;
    private final Map<String, Boolean> students = new HashMap<>();


    @Override
    public void printTotalStudentsGrades() {
        System.out.println("Total results of exams are:");
        students.forEach((studentFio, isPassed) ->
                System.out.println("Student: " + studentFio + ", is passed " + isPassed)
        );
    }

    @Override
    public void startNewExam() {
        String fio = askFio();
        int score = 0;
        List<Question> questions = questionsDao.getQuestions();
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
