package ru.otus.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.util.List;

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
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void shouldCountReturn2() {
        Mockito.doReturn(2).when(bookRepository).count();

        assertThat(bookService.count()).isEqualTo(2);
    }

    @Test
    void shouldReadBookReturnAlise() {
        int id = 3;
        Book book = Book.builder()
                .genre(List.of(new Genre(-1, "fantasy")))
                .name("Alice's Adventures in Wonderland")
                .author(new Author(-1, "Lewis Carroll"))
                .build();

        Mockito.doReturn(book).when(bookRepository).findById(id);

        assertThat(bookService.readBookById(id))
                .get()
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

        Mockito.doReturn(currentCount).when(bookRepository).count();

        int id = currentCount + 1;

        assertThat(bookService.createBookWithoutId(name, author, List.of(genre)))
                .isEqualTo(id);

        Mockito.verify(bookRepository, times(1)).save(any());
    }

    @Test
    void shouldUpdateBookAndCheckMethodsCalling() {
        String genre = "drama";
        String author = "James Francis Cameron";
//переделать
//        Book book = Book.builder()
//                .genre(genre)
//                .author(author)
//                .build();
//
//        bookService.updateBook(book);
//
//        Mockito.verify(authorService, times(1)).addAuthorIfDoesntExist(author);
//        Mockito.verify(genreService, times(1)).addGenreIfDoesntExist(genre);
//        Mockito.verify(bookRepository, times(1)).updateBook(book);
    }

    @Test
    void shouldDeleteBookById() {
        int id = 1;

        bookService.deleteBookById(id);

        Mockito.verify(bookRepository, times(id)).deleteBookById(id);
    }
}