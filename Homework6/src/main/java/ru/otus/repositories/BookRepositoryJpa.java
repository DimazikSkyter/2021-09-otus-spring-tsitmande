package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Component;
import ru.otus.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    @EntityGraph(attributePaths = "author")
    public List<Book> getBooksByAuthor(String author) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join b.author a where a.author = :author", Book.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    @Override
    @EntityGraph(attributePaths = "genre")
    public List<Book> getBooksByGenre(String genre) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre g where g.genre = :genre", Book.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteBookById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
