package ru.otus.domain;

import lombok.Data;

import java.util.List;

@Data
public class Answer {

    private final List<String> variants;

    @Override
    public String toString() {
        return String.join(" ", variants);
    }
}
