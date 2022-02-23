package ru.otus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findGenreByGenre(String genre);
}
