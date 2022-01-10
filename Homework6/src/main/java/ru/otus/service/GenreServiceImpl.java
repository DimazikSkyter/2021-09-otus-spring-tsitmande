package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Genre;
import ru.otus.repositories.GenreRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public void addGenreIfDoesntExist(String genreName) {
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        if (optionalGenre.isEmpty()) {
            Genre genre = new Genre(-1, genreName);
            genreRepository.save(genre);
        }

    }
}
