package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void count() {
        int count = bookDaoJdbc.count();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void getBookById() {
        Book book = bookDaoJdbc.getBookById(2);
        assertThat(book)
                .extracting(
                        Book::getAuthor,
                        Book::getName,
                        Book::getGenre
                )
                .containsExactly("oba", "death note", "anime");
    }

    @Test
    void getBooksByAuthor() {
        List<Book> booksByOba = bookDaoJdbc.getBooksByAuthor("oba");
        assertThat(booksByOba)
                .hasSize(1);
    }

    @Test
    void getBooksByGenre() {
        List<Book> booksByGenre = bookDaoJdbc.getBooksByGenre("fantastic");
        assertThat(booksByGenre)
                .hasSize(1)
                .element(0)
                .extracting(Book::getName,
                        Book::getAuthor,
                        Book::getGenre)
                .containsExactly("matrix", "vachovski", "fantastic");
    }

    @Test
    void createNewBook() {
        bookDaoJdbc.createNewBook(3, "lotr", "jackson", "fantasy");
        assertThat(bookDaoJdbc.count()).isEqualTo(3);
    }

    @Test
    void updateBookById() {
        Book book = Book.builder()
                .id(2)
                .name("Alice's Adventures in Wonderland")
                .genre("fantasy")
                .author("Lewis Carroll")
                .build();
        bookDaoJdbc.updateBook(book);
        Book bookUpdated = bookDaoJdbc.getBookById(2);
        assertThat(bookUpdated)
                .extracting(Book::getName,
                        Book::getAuthor,
                        Book::getGenre)
                .containsExactly("Alice's Adventures in Wonderland", "Lewis Carroll", "fantasy");
    }

    @Test
    void deleteBookById() {
        bookDaoJdbc.deleteBookById(1);
        assertThat(bookDaoJdbc.count()).isEqualTo(1);
        assertThat(bookDaoJdbc.getBookById(2)).isNotNull();
    }
}