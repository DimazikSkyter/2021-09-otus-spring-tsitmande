package ru.otus.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Author.builder()
                .id(rs.getLong("id"))
                .author(rs.getString("author"))
                .build();
    }
}
