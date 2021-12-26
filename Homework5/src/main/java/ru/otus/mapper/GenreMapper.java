package ru.otus.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .genre(rs.getString("genre"))
                .build();
    }
}
