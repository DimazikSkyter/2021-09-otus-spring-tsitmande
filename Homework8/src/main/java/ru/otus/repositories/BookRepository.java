package ru.otus.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.Book;


public interface BookRepository extends MongoRepository<Book, String> {

}
