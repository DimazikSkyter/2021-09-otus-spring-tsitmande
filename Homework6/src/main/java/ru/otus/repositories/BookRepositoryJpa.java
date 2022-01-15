package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.otus.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
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
    public List<Book> getBooksByAuthor(String author) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author a where a.author = :author", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("author", author);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre g where g.genre = :genre", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        query.setParameter("genre", genre);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        em.detach(book);
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteBookById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
