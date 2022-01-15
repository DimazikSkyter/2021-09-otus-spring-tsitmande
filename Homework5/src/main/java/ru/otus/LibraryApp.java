package ru.otus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class LibraryApp {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(LibraryApp.class);
//        Console.main(args);
    }
}
