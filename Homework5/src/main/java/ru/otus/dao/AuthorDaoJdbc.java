package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.mapper.AuthorMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;

    @Override
    public List<Author> getAuthorList() {
        return jdbc.query("SELECT * FROM AUTHORS " , new AuthorMapper());
    }

    @Override
    public void addNewAuthor(Author author) {
        jdbc.update("INSERT INTO AUTHORS (`ID`, `AUTHOR`) VALUES ( ?, ? )", author.getId(), author.getAuthor());
    }
}
