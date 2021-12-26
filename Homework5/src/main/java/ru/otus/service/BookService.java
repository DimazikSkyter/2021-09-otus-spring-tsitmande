package ru.otus.service;

import ru.otus.domain.Book;

public interface BookService {

    Book readBookById(long id);

    void createBookWithoutId(String name, String author, String genre);

    void updateBook(Book book);

    void deleteBookById(long id);
}
