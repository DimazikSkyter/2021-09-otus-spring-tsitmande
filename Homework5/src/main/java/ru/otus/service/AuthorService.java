package ru.otus.service;

public interface AuthorService {

    long addAuthorIfDoesntExist(String author);
}
