package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.util.Collections;

import static java.math.BigInteger.*;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "tsitmande", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insert authors, genres and book", author = "tsitmande")
    public void insertTwoBooks(BookRepository bookRepository) {
        Author author1 = new Author("0", "Pushkin");
        Author author2 = new Author("1", "Lermontov");

        Genre genre = new Genre("0", "novel");

        Book book1 = new Book("0", "book1", author1, Collections.emptyList(), Collections.singletonList(genre));
        Book book2 = new Book("1", "book2", author2, Collections.emptyList(), Collections.singletonList(genre));

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
