package ru.otus.mapper;

import ru.otus.domain.Book;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .author(rs.getString("author"))
                .genre(rs.getString("genre"))
                .build();
    }
}
