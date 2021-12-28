package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.mapper.BookMapper;

import java.util.List;


@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;

    @Override
    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM BOOKS", Integer.class);
    }

    @Override
    public Book getBookById(long id) {
        return jdbc.queryForObject("SELECT * FROM BOOKS WHERE id = ?", new BookMapper(), id);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return jdbc.query("SELECT * FROM BOOKS WHERE author = ?" , new BookMapper(), author);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return jdbc.query("SELECT * FROM BOOKS WHERE genre = ?", new BookMapper(), genre);
    }

    @Override
    public void createNewBook(long id, String name, String author, String genre) {
        jdbc.update("INSERT INTO BOOKS (`ID`, `NAME`, `GENRE`, `AUTHOR`) VALUES (?, ?, ?, ?)", id, name, genre, author);
    }

    @Override
    public void updateBook(Book book) {
        Book updatingBook = getBookById(book.getId());
        checkAndUpdateGenre(book, updatingBook);
        checkAndUpdateAuthor(book, updatingBook);
        jdbc.update("UPDATE BOOKS SET `GENRE` = ?, `AUTHOR` = ? WHERE `ID` = ?", book.getGenre(), book.getAuthor(), book.getId());
    }

    @Override
    public void deleteBookById(long id) {
        jdbc.update("DELETE FROM BOOKS WHERE ID = ?", id);
    }

    private void checkAndUpdateAuthor(Book book, Book updatingBook) {
        if (book.getAuthor() == null) {
            book.setAuthor(updatingBook.getAuthor());
        }
    }

    private void checkAndUpdateGenre(Book book, Book updatingBook) {
        if (book.getGenre() == null) {
            book.setGenre(updatingBook.getGenre());
        }
    }
}
