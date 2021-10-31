package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Answer {

    @Override
    public String toString() {
        return String.join(" ", variants);
    }

    private final List<String> variants;

    private final String realAnswer;
}
