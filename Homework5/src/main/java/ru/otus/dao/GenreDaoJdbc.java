package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;
import ru.otus.mapper.GenreMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbc;
    
    @Override
    public List<Genre> getGenreList() {
        return jdbc.query("SELECT * FROM GENRES" , new GenreMapper());
    }

    @Override
    public void addNewGenre(Genre genre) {
        jdbc.update("INSERT INTO GENRES (`ID`, `GENRE`) VALUES ( ?, ? )", genre.getId(), genre.getGenre());
    }
}
