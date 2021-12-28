package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public void addGenreIfDoesntExist(String genreName) {
        List<Genre> authorList = genreDao.getGenreList();
        Genre genre = new Genre(authorList.size() + 1, genreName);
        if (!authorList.contains(genre)) {
            genreDao.addNewGenre(genre);
        }
    }
}
