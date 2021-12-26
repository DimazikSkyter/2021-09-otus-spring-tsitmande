package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getGenreList();

    void addNewGenre(Genre genre);
}
