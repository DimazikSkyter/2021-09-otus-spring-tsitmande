package ru.otus.repositories;

import ru.otus.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> findByName(String genre);

    List<Genre> getGenreList();

    Genre save(Genre genre);
}
