package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public long addGenreIfDoesntExist(String genreName) {
        List<Genre> genreList = genreDao.getGenreList();
        Optional<Genre> optionalGenre = genreList.stream().filter(genre -> genre.getGenre().equals(genreName)).findAny();
        long id = getCurrentId(genreList, optionalGenre);
        if (optionalGenre.isEmpty()) {
            Genre genre = new Genre(id, genreName);
            genreDao.addNewGenre(genre);
        }
        return id;
    }

    private long getCurrentId(List<Genre> genreList, Optional<Genre> optionalGenre) {
        if (optionalGenre.isPresent()) {
            return optionalGenre.get().getId();
        }
        return genreList.size() + 1;
    }
}
