package ru.otus.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> findByName(String author) {
        TypedQuery<Author> query = em.createQuery("select a " +
                        "from Author a " +
                        "where a.author = :author",
                Author.class);
        query.setParameter("author", author);
        List<Author> authors = query.getResultList();
        if (authors.size() > 0) {
            return Optional.of(authors.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<Author> getAuthorList() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }
}
