package ru.otus.service;

import ru.otus.domain.Book;

public interface BookService {

    int count();

    Book readBookById(long id);

    long createBookWithoutId(String name, String author, String genre);

    void updateBook(long id, String author, String genre);

    void deleteBookById(long id);
}
