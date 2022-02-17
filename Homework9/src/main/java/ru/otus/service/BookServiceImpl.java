package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;
import ru.otus.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public List<Book> getAllBooks() {
        log.info("Get all books from repository");
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> readBookById(long id) {
        log.info("Read book with id {}", id);
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public long createBook(Book book) {
        log.debug("Create new book {}.", book);
        refillGenreId(book);
        Book savedBook = bookRepository.save(book);
        log.info("Book saved with id {}.", savedBook.getId());
        return savedBook.getId();
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        refillGenreId(book);
        bookRepository.save(book);
        log.info("Book {} successfully updated.", book);
    }

    @Override
    public void deleteBookById(long id) {
        log.debug("Trying delete book with id {}", id);
        bookRepository.deleteById(id);
        log.info("Book with id {} successfully deleted", id);
    }

    @Transactional
    @Override
    public void addNewCommentForBookById(long id, String comment) {
        log.debug("Trying add comment to book with id {}.", id);

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new NullPointerException("Book with id " + id + " was not found.");
        }

        Book book = optionalBook.get();
        book.getComment().add(new Comment(null, book, comment));
        bookRepository.save(book);
        log.info("Book {} successfully updated.", book);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        Author authorExample = new Author(null, author);
        Book bookExample = Book.builder()
                .author(authorExample)
                .build();
        return bookRepository.findAll(Example.of(bookExample, ExampleMatcher.matchingAny()));
    }

    private void refillGenreId(Book book) {
        book.getGenre().forEach(
                genre -> {
                    genre.setId(null);
                    genreRepository.findGenreByGenre(genre.getGenre()).ifPresent(
                            dbGenre ->
                                    genre.setId(dbGenre.getId())
                    );
                }
        );
    }
}
