package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAuthorList();

    void addNewAuthor(Author author);
}
