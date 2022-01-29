package ru.otus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

//    @Transactional(propagation = Propagation.REQUIRED)
//    List<Book> findBookByAuthor(Author author);

//    @Transactional(propagation = Propagation.REQUIRED)
//    List<Book> findBookByGenre(Genre genre);
}
