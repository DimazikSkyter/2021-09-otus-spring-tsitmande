package ru.otus.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCount2() {
        assertThat(repositoryJpa.getAuthorList().size()).isEqualTo(2);
    }

    @DisplayName("Загрузка ожидаемой книги")
    @Test
    void shouldFindExpectedStudentById() {
        System.out.println(repositoryJpa.getAuthorList().size());
//        val optionalActualStudent = repositoryJpa.findById(FIRST_STUDENT_ID);
//        val expectedStudent = em.find(OtusStudent.class, FIRST_STUDENT_ID);
//        assertThat(optionalActualStudent).isPresent().get()
//                .usingRecursiveComparison().isEqualTo(expectedStudent);
    }

    @Test
    void shouldSaveNewAuthor() {
        Author author = new Author(0L, "asdasda");
        em.persist(author);
        em.detach(author);
    }
}