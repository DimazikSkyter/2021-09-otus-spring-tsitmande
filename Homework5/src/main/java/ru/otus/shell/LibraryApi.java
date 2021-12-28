package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class LibraryApi {

    private final BookService bookService;

    @ShellMethod("Сколько всего есть книг")
    public String countBook() {
        return String.valueOf(bookService.count());
    }

    @ShellMethod("Прочитать объект книгу по id")
    public String readBook(@ShellOption long id) {
        Book book = bookService.readBookById(id);
        return book.toString();
    }

    @ShellMethod("Создать книгу")
    public String createNewBook(@ShellOption String name, @ShellOption String author, @ShellOption String genre) {
        long id = bookService.createBookWithoutId(name, author, genre);
        return String.format("Book %s with id %s, with author %s and genre %s was created", id, name, author, genre);
    }

    @ShellMethod("Обновить книгу")
    public String updateBookById(@ShellOption long id, @ShellOption String genre, @ShellOption String author) {
        bookService.updateBook(Book.builder()
                .author(author)
                .genre(genre)
                .id(id)
                .build());
        return "Book with id " + id + " was updated.";
    }

    @ShellMethod("Удалить книгу")
    public String deleteBookById(@ShellOption long id) {
        bookService.deleteBookById(id);
        return "Book with id " + id + " was deleted.";
    }
}
