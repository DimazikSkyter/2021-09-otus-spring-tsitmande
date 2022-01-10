package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.model.Author;
import ru.otus.repositories.AuthorRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void addAuthorIfDoesntExist(String authorName) {
        Optional<Author> optionalAuthor = authorRepository.findByName(authorName);
        if (optionalAuthor.isEmpty()) {
            Author author = new Author(-1, authorName);
            authorRepository.save(author);
        }

    }
}
