package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Book;
import ru.otus.repositories.BookRepository;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class LibraryController {

    private static final String CONTENT_TYPE = "application/json";
    @Autowired
    private final BookRepository bookRepository;

    @GetMapping
    public Flux<Book> listOfBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Book> byId(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping(produces = CONTENT_TYPE, consumes = CONTENT_TYPE)
    public Mono<String> createNewBook(@RequestBody Book book) {
        return bookRepository.save(book).map(book1 -> book.getId());
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable String id, @RequestBody Book book) throws Exception {
        if(!id.equals(book.getId())) {
            throw new Exception("Wrong book id in path " + id);
        }
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public Mono<String> deleteBook(@PathVariable String id) throws Exception {

        return bookRepository.deleteById(id).then(Mono.just("Book with id " + id + " was successfully deleted."));
    }
}
