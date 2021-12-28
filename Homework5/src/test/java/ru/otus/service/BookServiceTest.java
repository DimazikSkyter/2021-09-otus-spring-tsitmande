package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dao.BookDao;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private AuthorServiceImpl authorService;
    @Mock
    private GenreServiceImpl genreService;
    @Mock
    private BookDaoJdbc bookDao;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void shouldCountReturn2() {
        Mockito.doReturn(2).when(bookDao).count();

        assertThat(bookService.count()).isEqualTo(2);
    }

    @Test
    void shouldReadBookReturnAlise() {
        int id = 3;
        Book book = Book.builder()
                .genre("fantasy")
                .name("Alice's Adventures in Wonderland")
                .author("Lewis Carroll")
                .build();

        Mockito.doReturn(book).when(bookDao).getBookById(id);

        assertThat(bookService.readBookById(id))
                .extracting(Book::getName,
                        Book::getAuthor,
                        Book::getGenre)
                .containsExactly("Alice's Adventures in Wonderland", "Lewis Carroll", "fantasy");
    }

    @Test
    void shouldCreateBookAndReturnItId() {
        int currentCount = 3;

        String name = "Alice's Adventures in Wonderland";
        String genre = "fantasy";
        String author = "Lewis Carroll";

        Mockito.doReturn(currentCount).when(bookDao).count();

        assertThat(bookService.createBookWithoutId(name, author, genre))
                .isEqualTo(currentCount);

        Mockito.verify(bookDao, times(1)).createNewBook(currentCount, name, author, genre);
    }

    @Test
    void shouldUpdateBookAndCheckMethodsCalling() {
        String genre = "drama";
        String author = "James Francis Cameron";

        Book book = Book.builder()
                .genre(genre)
                .author(author)
                .build();

        bookService.updateBook(book);

        Mockito.verify(authorService, times(1)).addAuthorIfDoesntExist(author);
        Mockito.verify(genreService, times(1)).addGenreIfDoesntExist(genre);
        Mockito.verify(bookDao, times(1)).updateBook(book);
    }

    @Test
    void shouldDeleteBookById() {
        int id = 1;

        bookService.deleteBookById(id);

        Mockito.verify(bookDao, times(id)).deleteBookById(id);
    }
}