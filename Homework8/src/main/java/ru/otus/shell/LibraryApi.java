package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Optional;

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
    public String readBook(@ShellOption String id) {
        Optional<Book> book = bookService.readBookById(id);
        if (book.isPresent()) {
            return book.get().toString();
        } else {
            return String.format("Book with id %s wasn't found", id);
        }
    }

    @ShellMethod("Добавить коммент к книге по id")
    public String addComment(@ShellOption String id, @ShellOption String comment) {
        bookService.addNewCommentForBookById(id, comment);
        return "The comment was added to book with id " + id;
    }

    @ShellMethod("Создать книгу")
    public String createNewBook(@ShellOption String name, @ShellOption String author, @ShellOption List<String> genre) {
        String id = bookService.createBookWithoutId(name, author, genre);
        return String.format("Book with id %s, with name %s, with author %s and genre %s was created", id, name, author, genre);
    }

    @ShellMethod("Обновить книгу")
    public String updateBookById(@ShellOption String id, @ShellOption(defaultValue = ShellOption.NULL) String name,
                                 @ShellOption(defaultValue = ShellOption.NULL) List<String> genre,
                                 @ShellOption(defaultValue = ShellOption.NULL) String author) {
        bookService.updateBook(id, name, author, genre);
        return "Book with id " + id + " was updated.";
    }

    @ShellMethod("Удалить книгу")
    public String deleteBookById(@ShellOption String id) {
        bookService.deleteBookById(id);
        return "Book with id " + id + " was deleted.";
    }
}
