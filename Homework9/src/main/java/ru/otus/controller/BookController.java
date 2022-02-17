package ru.otus.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Book;
import ru.otus.service.BookService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private static final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private final BookService bookService;

    @GetMapping(value = "/count", produces = CONTENT_TYPE)
    public ResponseEntity<String> countBooks() throws JsonProcessingException {
        return ResponseEntity.ok(
                wrapSingleValue("count", String.valueOf(bookService.count()))
        );
    }

    @GetMapping(value = "/{id}", produces = CONTENT_TYPE)
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
        Book book = bookService.readBookById(id)
                .orElseThrow(NullPointerException::new);
        return ResponseEntity.ok(book);
    }

    @GetMapping(value = "/list", produces = CONTENT_TYPE)
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping(produces = CONTENT_TYPE, consumes = CONTENT_TYPE)
    public ResponseEntity<String> createNewBook(@RequestBody Book book) throws JsonProcessingException {
        long id = bookService.createBook(book);
        return ResponseEntity.ok(wrapSingleValue("id", String.valueOf(id)));
    }

    @PutMapping(value = "/{id}")
    public String updateBook(@PathVariable("id") long id,
                             @RequestBody Book book) throws Exception {
        if(id != book.getId()) {
            throw new Exception("Wrong book id in path " + id);
        }
        bookService.updateBook(book);
        return wrapSingleValue("message", "Entity with id " + id + " was successfully updated.");
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) throws JsonProcessingException {
        bookService.deleteBookById(id);
        return wrapSingleValue("message", "Entity with id " + id + " was successfully deleted.");
    }

    private String wrapSingleValue(String name, String value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(Collections.singletonMap(name, value));
    }
}
