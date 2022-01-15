package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BookDao {

    int count();

    Book getBookById(long id);

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByGenre(String genre);

    void createNewBook(long id, String name, long authorId, long genreId);

    void updateBook(long id, long authorId, long genreId);

    void deleteBookById(long id);
}
