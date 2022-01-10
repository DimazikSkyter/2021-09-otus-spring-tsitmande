package ru.otus.repositories;

import ru.otus.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByName(String author);

    List<Author> getAuthorList();

    Author save(Author author);
}
