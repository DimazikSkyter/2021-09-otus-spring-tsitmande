package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void shouldCountReturn2() {
        Mockito.doReturn(2L).when(bookRepository).count();

        assertThat(bookService.count()).isEqualTo(2L);
    }

    @Test
    void shouldReadBookReturnAlise() {
        String id = "3";
        Book book = Book.builder()
                .genre(List.of(new Genre(null, "fantasy")))
                .name("Alice's Adventures in Wonderland")
                .author("Lewis Carroll")
                .build();

        Mockito.doReturn(Optional.of(book)).when(bookRepository).findById(id);

        assertThat(bookService.readBookById(id))
                .get()
                .extracting(Book::getName,
                        Book::getAuthor,
                        b -> b.getGenre().get(0).getGenre())
                .containsExactly("Alice's Adventures in Wonderland", "Lewis Carroll", "fantasy");
    }

    @Test
    void shouldCreateBookAndReturnItId() {
        String name = "Alice's Adventures in Wonderland";
        String genre = "fantasy";
        String author = "Lewis Carroll";

        Mockito.doReturn(Book.builder().id("4").build()).when(bookRepository).save(any());

        bookService.createBookWithoutId(name, author, List.of(genre));

        Mockito.verify(bookRepository, times(1)).save(any());
    }

    @Test
    void shouldAddComment() {
        String comment = "new comment 1";
        Book book = Book.builder()
                .id("1")
                .comment(new ArrayList<>()).build();

        Mockito.doReturn(Optional.of(book)).when(bookRepository).findById("1");

        bookService.addNewCommentForBookById("1", comment);

        Mockito.verify(bookRepository, times(1)).save(any());
    }

    @Test
    void shouldDeleteBookById() {
        String id = "1";

        bookService.deleteBookById(id);

        Mockito.verify(bookRepository, times(1)).deleteById(id);
    }
}