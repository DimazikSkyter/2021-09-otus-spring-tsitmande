package ru.otus.model.listners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import ru.otus.model.Book;
import ru.otus.model.Comment;
import ru.otus.model.Genre;

import java.util.List;
import java.util.Optional;

@Component
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Book> {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Book> event) {
        Book book = event.getSource();

        Optional.ofNullable(book.getGenre()).ifPresent(this::saveAllGenre);
        Optional.ofNullable(book.getComment()).ifPresent(this::saveAllComments);

        super.onBeforeSave(event);
    }

    private void saveAllComments(List<Comment> comments) {
        for(Comment comment : comments) {
            saveComments(comment);
        }
    }

    private void saveAllGenre(List<Genre> genres) {
        for(Genre genre : genres) {
            saveGenre(genre);
        }
    }

    private void saveGenre(Genre genre) {
        mongoOperations.save(genre);
    }

    private void saveComments(Comment comment) {
        mongoOperations.save(comment);
    }
}
