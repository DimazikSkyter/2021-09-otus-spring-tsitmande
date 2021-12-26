package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void getAuthorList() {
        List<Genre> genres = genreDaoJdbc.getGenreList();
        assertThat(genres)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    void addNewGenre() {
        Genre author = Genre.builder()
                .id(3)
                .genre("History")
                .build();
        genreDaoJdbc.addNewGenre(author);
        assertThat(genreDaoJdbc.getGenreList()).hasSize(3);
    }
}