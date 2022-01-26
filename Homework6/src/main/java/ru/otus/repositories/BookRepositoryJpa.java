package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Component;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import javax.persistence.*;
import javax.persistence.criteria.*;
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
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> itemRoot = criteriaQuery.from(Book.class);
        Predicate authorNamePredicate = criteriaBuilder.equal(itemRoot.get("author").get("author"), author);
        criteriaQuery.where(authorNamePredicate);
        TypedQuery<Book> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @EntityGraph(attributePaths = "genre")
    public List<Book> getBooksByGenre(String genre) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> itemRoot = criteriaQuery.from(Book.class);
        Join<Book, Genre> join = itemRoot.join("genre", JoinType.INNER);
        Predicate genreNamePredicate = criteriaBuilder.like(join.get("genre"), "%" + genre + "%");

        //        Predicate genreNamePredicate = criteriaBuilder.equal(itemRoot.get("genre").get("genre"), genre);
        criteriaQuery.where(genreNamePredicate);
        TypedQuery<Book> query = em.createQuery(criteriaQuery);
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
