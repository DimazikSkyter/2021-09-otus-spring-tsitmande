package ru.otus.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
