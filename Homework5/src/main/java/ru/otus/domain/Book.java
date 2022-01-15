package ru.otus.domain;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class Book {

    private final long id;
    private final String name;
    private String author;
    private String genre;
}
