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
        return jdbc.queryForObject("SELECT b.id, b.name, a.author as author, g.genre as genre FROM BOOKS b " +
                "left JOIN GENRES g on b.genre_id = g.id " +
                "left JOIN AUTHORS a on b.author_id = a.id " +
                "WHERE b.id = ?", new BookMapper(), id);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return jdbc.query("SELECT b.id, b.name, a.author as author, g.genre as genre FROM BOOKS b " +
                "left JOIN GENRES g on b.genre_id = g.id " +
                "left JOIN AUTHORS a on b.author_id = a.id " +
                "WHERE a.author = ?" , new BookMapper(), author);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return jdbc.query("SELECT b.id, b.name, a.author as author, g.genre as genre FROM BOOKS b " +
                "left JOIN GENRES g on b.genre_id = g.id " +
                "left JOIN AUTHORS a on b.author_id = a.id " +
                "WHERE g.genre = ?", new BookMapper(), genre);
    }

    @Override
    public void createNewBook(long id, String name, long authorId, long genreId) {
        jdbc.update("INSERT INTO BOOKS (`ID`, `NAME`, `GENRE_ID`, `AUTHOR_ID`) VALUES (?, ?, ?, ?)", id, name, genreId, authorId);
    }

    @Override
    public void updateBook(long id, long authorId, long genreId) {
        jdbc.update("UPDATE BOOKS SET `AUTHOR_ID` = ?, `GENRE_ID` = ? WHERE `ID` = ?", authorId, genreId, id);
    }

    @Override
    public void deleteBookById(long id) {
        jdbc.update("DELETE FROM BOOKS WHERE ID = ?", id);
    }
}
