package ru.otus.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Answer {

    @Getter
    private final List<String> variants;

    @Override
    public String toString() {
        return String.join(" ", variants);
    }
}
