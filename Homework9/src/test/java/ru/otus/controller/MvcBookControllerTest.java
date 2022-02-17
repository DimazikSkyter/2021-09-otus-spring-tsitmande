package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.BookServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class MvcBookControllerTest {

    @MockBean
    BookServiceImpl bookService;

    @Autowired
    @InjectMocks
    BookController bookController;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getBookById() throws Exception {

        doReturn(2L).when(bookService).count();

        mockMvc.perform(get("/books/count"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count")
                        .value("2"));
    }

    @Test
    void readBookById() throws Exception {
        long id = 1;
        String name = "death souls";
        String author = "Gogol";
        String genreName = "poem";
        Set<Genre> genre = Set.of(new Genre(1L, genreName));

        doReturn(
                Optional.of(
                        Book.builder()
                                .id(id)
                                .name(name)
                                .author(new Author(1L, author))
                                .genre(genre)
                                .build()
                )
        ).when(bookService).readBookById(id);

        mockMvc.perform(get("/books/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(id))
                .andExpect(jsonPath("$.name")
                        .value(name))
                .andExpect(jsonPath("$.author.author")
                        .value(author))
                .andExpect(jsonPath("$.genre[0].genre")
                        .value(genreName));
    }

    @Test
    void createNewBook() throws Exception {

        String name = "death souls";
        String author = "Gogol";
        Set<Genre> genre = Set.of(new Genre(null, "poem"));

        Book book = Book.builder()
                .name(name)
                .author(new Author(null, author))
                .genre(genre)
                .build();

        doReturn(3L).when(bookService).createBook(Mockito.any());
        mockMvc.perform(post("/books")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value("3"));
    }
}