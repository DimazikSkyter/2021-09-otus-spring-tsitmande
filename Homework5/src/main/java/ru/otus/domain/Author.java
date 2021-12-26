package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class Author {

    private final long id;
    private final String author;

//    private final List<Book> books;
}
