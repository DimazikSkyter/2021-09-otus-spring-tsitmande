package ru.otus.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String name;
    private String author;
    @DBRef
    private List<Comment> comment;
    @DBRef
    private List<Genre> genre;
}
