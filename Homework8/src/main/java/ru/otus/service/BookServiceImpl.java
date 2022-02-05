package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.math.BigInteger;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> readBookById(String id) {
        log.info("Read book with id {}", id);
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public String createBookWithoutId(String name, String author, List<String> genreNames) {
        log.debug("Create new book with name {}, author {}, genreNames {}.", name, author, genreNames);
        List<Genre> genres = genreNames.stream().map(g -> new Genre(null, g)).collect(Collectors.toList());
        Book book = Book.builder()
                .author(new Author(null, author))
                .genre(genres)
                .name(name)
                .build();
        Book savedBook = bookRepository.save(book);
        log.info("Book with id {}, name {}, author {}, genreNames {} successfully created.", savedBook.getId(), name, author, genreNames);
        return savedBook.getId();
    }

    @Transactional
    @Override
    public void updateBook(String id, String name, String author, List<String> genreNames) {
        log.debug("Trying update book with id {},  name {}, author {}, genreNames {}.", id, name, author, genreNames);

        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()) {
            throw new NullPointerException("Book with id " + id + " was not found.");
        }
        Book book = optionalBook.get();
        Optional.ofNullable(name).ifPresent(book::setName);
        Optional.ofNullable(author).ifPresent(a -> {
            Author newAuthor = new Author(null, author);
            book.setAuthor(newAuthor);
        });
        Optional.ofNullable(genreNames).ifPresent(g -> {
            book.getGenre().clear();
            List<Genre> genres = genreNames.stream().map(genreName -> new Genre(null, genreName)).collect(Collectors.toList());
            book.getGenre().addAll(genres);
        });
        bookRepository.save(book);
        log.info("Book {} successfully updated.", book);
    }

    @Transactional
    @Override
    public void deleteBookById(String id) {
        log.debug("Trying delete book with id {}", id);
        bookRepository.deleteById(id);
        log.info("Book with id {} successfully deleted", id);
    }

    @Transactional
    @Override
    public void addNewCommentForBookById(String id, String comment) {
        log.debug("Trying add comment to book with id {}.", id);

        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()) {
            throw new NullPointerException("Book with id " + id + " was not found.");
        }

        Book book = optionalBook.get();
        book.getComment().add(new Comment(null, book, comment));
        bookRepository.save(book);
        log.info("Book {} successfully updated.", book);
    }

}
