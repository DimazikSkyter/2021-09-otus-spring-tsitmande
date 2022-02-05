package ru.otus.service;

import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    Optional<Book> readBookById(long id);

    long createBookWithoutId(String name, String author, List<String> genre);

    void updateBook(long id, String name, String author, List<String> genre);

    void deleteBookById(long id);

    void addNewCommentForBookById(long id, String comment);

    List<Book> findBooksByAuthor(String author);
}
