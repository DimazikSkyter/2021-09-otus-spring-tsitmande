package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public long addAuthorIfDoesntExist(String authorName) {
        List<Author> authorList = authorDao.getAuthorList();
        Optional<Author> optionalAuthor = authorList.stream().filter(author -> author.getAuthor().equals(authorName)).findAny();
        long id = getCurrentId(authorList, optionalAuthor);
        if (optionalAuthor.isEmpty()) {
            Author author = new Author(id, authorName);
            authorDao.addNewAuthor(author);
        }
        return id;
    }

    private long getCurrentId(List<Author> authorList, Optional<Author> optionalAuthor) {
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get().getId();
        }
        return authorList.size() + 1;
    }
}
