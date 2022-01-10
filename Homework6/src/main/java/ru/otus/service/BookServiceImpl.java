package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final GenreService genreService;
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

    @Override
    public long createBookWithoutId(String name, String author, List<String> genreNames) {
        log.debug("Create new book with name {}, author {}, genreNames {}.", name, author, genreNames);
        List<Genre> genres = genreNames.stream().map(g -> new Genre(-1, g)).collect(Collectors.toList());
        Book book = Book.builder()
                .author(new Author(-1, author))
                .genre(genres)
                .name(name)
                .build();
        bookRepository.save(book);
        log.info("Book with name {}, author {}, genreNames {} successfully created.", name, author, genreNames);
        return book.getId();
    }

    @Override
    public void updateBook(long id, String name, String author, List<String> genreNames) {
        log.debug("Trying update book with name {}, author {}, genreNames {}.", name, author, genreNames);
        List<Genre> genres = genreNames.stream().map(g -> new Genre(-1, g)).collect(Collectors.toList());
        Book book = Book.builder()
                .author(new Author(-1, author))
                .genre(genres)
                .name(name)
                .build();
//        checkAndUpdateGenreAndAuthor(book.getGenre(), book.getAuthor());
        bookRepository.save(book);
        log.info("Book {} successfully created.", book);
    }

    @Override
    public void deleteBookById(long id) {
        log.debug("Trying delete book with id {}", id);
        bookRepository.deleteBookById(id);
        log.info("Book with id {} successfully deleted", id);
    }

    //todo возможно нужно удалить
//    private void checkAndUpdateGenreAndAuthor(List<Genre> genre, String author) {
//        authorService.addAuthorIfDoesntExist(author);
//        genreService.addGenreIfDoesntExist(genre);
//    }
}
