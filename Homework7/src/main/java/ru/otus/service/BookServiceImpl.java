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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public Optional<Book> readBookById(long id) {
        log.info("Read book with id {}", id);
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public long createBookWithoutId(String name, String author, List<String> genreNames) {
        log.debug("Create new book with name {}, author {}, genreNames {}.", name, author, genreNames);
        List<Genre> genres = genreNames.stream().map(g -> new Genre(0L, g)).collect(Collectors.toList());
        Book book = Book.builder()
                .author(new Author(0L, author))
                .genre(genres)
                .name(name)
                .build();
        Book savedBook = bookRepository.save(book);
        log.info("Book with id {}, name {}, author {}, genreNames {} successfully created.", savedBook.getId(), name, author, genreNames);
        return savedBook.getId();
    }

    @Transactional
    @Override
    public void updateBook(long id, String name, String author, List<String> genreNames) {
        log.debug("Trying update book with id {},  name {}, author {}, genreNames {}.", id, name, author, genreNames);

        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()) {
            throw new NullPointerException("Book with id " + id + " was not found.");
        }
        Book book = optionalBook.get();
        Optional.ofNullable(name).ifPresent(book::setName);
        Optional.ofNullable(author).ifPresent(a -> {
            Author newAuthor = new Author(0L, author);
            book.setAuthor(newAuthor);
        });
        Optional.ofNullable(genreNames).ifPresent(g -> {
            book.getGenre().clear();
            List<Genre> genres = genreNames.stream().map(genreName -> new Genre(0L, genreName)).collect(Collectors.toList());
            book.getGenre().addAll(genres);
        });
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
        if(optionalBook.isEmpty()) {
            throw new NullPointerException("Book with id " + id + " was not found.");
        }

        Book book = optionalBook.get();
        book.getComment().add(new Comment(0L, book, comment));
        bookRepository.save(book);
        log.info("Book {} successfully updated.", book);
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        Author authorExample = new Author(0L, author);
        Book bookExample = Book.builder()
                .author(authorExample)
                .build();
        return bookRepository.findAll(Example.of(bookExample, ExampleMatcher.matchingAny()));
    }
}
