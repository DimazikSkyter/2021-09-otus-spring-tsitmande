package ru.otus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookDao bookDao;

    @Override
    public int count() {
        return bookDao.count();
    }

    @Override
    public Book readBookById(long id) {
        log.info("Read book with id {}", id);
        return bookDao.getBookById(id);
    }

    @Override
    public long createBookWithoutId(String name, String author, String genre) {
        log.debug("Create new book with name {}, author {}, genre {}.", name, author, genre);
        checkAndUpdateGenreAndAuthor(genre, author);
        int id = bookDao.count() + 1;
        bookDao.createNewBook(id, name, author, genre);
        log.info("Book with name {}, author {}, genre {} successfully created.", name, author, genre);
        return id;
    }

    @Override
    public void updateBook(Book book) {
        log.debug("Trying update book {}", book);
        checkAndUpdateGenreAndAuthor(book.getGenre(), book.getAuthor());
        bookDao.updateBook(book);
        log.info("Book {} successfully created.", book);
    }

    @Override
    public void deleteBookById(long id) {
        log.debug("Trying delete book with id {}", id);
        bookDao.deleteBookById(id);
        log.info("Book with id {} successfully deleted", id);
    }

    private void checkAndUpdateGenreAndAuthor(String genre, String author) {
        authorService.addAuthorIfDoesntExist(author);
        genreService.addGenreIfDoesntExist(genre);
    }
}
