package ru.otus.service;

import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    List<Book> getAllBooks();

    Optional<Book> readBookById(long id);

    long createBook(Book book);

    void updateBook(Book book);

    void deleteBookById(long id);

    void addNewCommentForBookById(long id, String comment);

    List<Book> findBooksByAuthor(String author);
}
