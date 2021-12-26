package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void getAuthorList() {
        List<Author> authors = authorDaoJdbc.getAuthorList();
        assertThat(authors)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    void addNewAuthor() {
        Author author = Author.builder()
                .id(3)
                .author("Pushkin")
                .build();
        authorDaoJdbc.addNewAuthor(author);
        assertThat(authorDaoJdbc.getAuthorList()).hasSize(3);
    }
}