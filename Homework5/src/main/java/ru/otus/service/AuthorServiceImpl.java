package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public void addAuthorIfDoesntExist(String authorName) {
        List<Author> authorList = authorDao.getAuthorList();
        Author author = new Author(authorList.size(), authorName);
        if (!authorList.contains(author)) {
            authorDao.addNewAuthor(author);
        }
    }
}
