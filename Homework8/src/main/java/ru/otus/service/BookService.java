package ru.otus.service;

import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    Optional<Book> readBookById(String id);

    String createBookWithoutId(String name, String author, List<String> genre);

    void updateBook(String id, String name, String author, List<String> genre);

    void deleteBookById(String id);

    void addNewCommentForBookById(String id, String comment);
}
