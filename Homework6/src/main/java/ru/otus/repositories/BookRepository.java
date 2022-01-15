package ru.otus.repositories;

import ru.otus.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Long count();

    Optional<Book> findById(long id);

    List<Book> getBooksByAuthor(String author);

    List<Book> getBooksByGenre(String genre);

    Book save(Book book);

    void deleteBookById(long id);
}
