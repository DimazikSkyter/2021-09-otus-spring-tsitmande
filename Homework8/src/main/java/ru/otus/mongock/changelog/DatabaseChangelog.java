package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;
import ru.otus.repositories.BookRepository;

import java.util.Collections;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "tsitmande", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insert authors, genres and book", author = "tsitmande")
    public void insertTwoBooks(BookRepository bookRepository) {

        Genre genre = new Genre("0", "novel");

        Book book1 = new Book("0", "book1", "Pushkin", Collections.emptyList(), Collections.singletonList(genre));
        Comment comment = new Comment("1", null, "asdasd");
        Book book2 = new Book("1", "book2", "Lermontov", Collections.singletonList(
                comment), Collections.singletonList(genre));
        comment.setBook(book2);

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
