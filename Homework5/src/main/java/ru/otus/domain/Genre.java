package ru.otus.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class Genre {

    private final long id;
    private final String genre;

//    private final List<Book> books;
}
