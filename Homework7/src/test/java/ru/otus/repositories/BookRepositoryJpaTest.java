package ru.otus.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

//    @Test
//    @Transactional
//    void shouldFindByAuthor() {
//        Author author = new Author(1L, "vachovski");
//        List<Book> books = bookRepositoryJpa.findBookByAuthor(author);
//        System.out.println("Size:"+  books.size());
//        for (Book book : books) {
//            System.out.println(book.getName());
//        }
//    }

    @Test
    void shouldReturnCorrectSizeOfRepository() {

        System.out.println(bookRepositoryJpa.count());
    }

    @Test
    @Transactional
    void shouldCorrectMapping() {
        String commentBody = "comment 1";
        Comment comment = new Comment(0L, null, commentBody);
        Book book = Book.builder()
                .author(new Author(0L, "Test author"))
                .genre(List.of(new Genre(0L, "genre 1"), new Genre(0L, "genre 2")))
                .name("test book")
                .comment(List.of(comment))
                .build();
        comment.setBook(book);
        bookRepositoryJpa.save(book);

        Book savedBook = bookRepositoryJpa.findById(3L).get();

        assertThat(savedBook)
                .isNotNull()
                .extracting(Book::getName,
                        b -> b.getGenre().size(),
                        b -> b.getAuthor().getAuthor(),
                        b -> b.getComment().get(0).getComment())
                .containsExactly(
                        book.getName(),
                        book.getGenre().size(),
                        book.getAuthor().getAuthor(),
                        commentBody
                );
    }
}