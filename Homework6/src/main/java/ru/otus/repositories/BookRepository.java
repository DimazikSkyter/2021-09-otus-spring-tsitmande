package ru.otus.repositories;

import org.springframework.data.repository.Repository;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

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
