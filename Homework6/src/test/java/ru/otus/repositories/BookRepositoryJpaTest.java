package ru.otus.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCorrectSizeOfRepository() {

        assertThat(bookRepositoryJpa.count()).isEqualTo(2);
    }

    @Test
    void shouldFindBookById() {
        Book book = bookRepositoryJpa.findById(1).get();
        assertThat(book)
                .extracting(Book::getName,
                        b -> b.getAuthor().getAuthor(),
                        b -> b.getGenre().get(0).getGenre(),
                        b -> b.getComment().size())
                .containsExactly("matrix", "vachovski", "triller", 3);
    }

    @Test
    void shouldReturnBooksByAuthor() {
        String author = "oba";
        List<Book> books = bookRepositoryJpa.getBooksByAuthor(author);

        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0))
                .extracting(Book::getName,
                        b -> b.getAuthor().getAuthor())
                .containsExactly("death note", author);
    }

    @Test
    void shouldReturnBooksByGenre() {
        String genre = "fantastic";
        List<Book> books = bookRepositoryJpa.getBooksByGenre(genre);

        assertThat(books.size()).isEqualTo(1);

        Book book = books.get(0);

        assertThat(book.getGenre())
                .flatExtracting(Genre::getGenre)
                .hasSameElementsAs(List.of(genre));
    }

    @Test
    void shouldSaveNewEntity() {
        Book book = Book.builder()
                .id(0L)
                .name("sherlok holmes")
                .genre(List.of(new Genre(0, "detective")))
                .author(new Author(0L, "Arthur Ignatius Conan Doyle"))
                .comment(List.of(new Comment(0L, null, "test comment")))
                .build();
        Book savedBook = bookRepositoryJpa.save(book);

        assertThat(savedBook.getId()).isEqualTo(3);
    }

    @Test
    void shouldUpdateEntity() {
        Book book = bookRepositoryJpa.findById(1).get();
        book.getComment().add(new Comment(0L, book, "comment"));
        book.setName("zzz");
        bookRepositoryJpa.save(book);
        em.flush();
        em.detach(book);
        em.clear();

        Book book1 = bookRepositoryJpa.findById(1).get();
        System.out.println(book1);
    }

    @Test
    void deleteBookById() {
        Book book = bookRepositoryJpa.findById(1).get();
        assertThat(book).isNotNull();
        bookRepositoryJpa.deleteBookById(1);
        em.flush();
        em.clear();
        boolean isEmpty = bookRepositoryJpa.findById(1).isEmpty();
        assertThat(isEmpty).isTrue();
    }
}