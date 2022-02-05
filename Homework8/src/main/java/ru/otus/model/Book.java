package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Document(collection = "books")
//todo subgraph в attributeNodes
public class Book {

    @Id
    private String id;
    private String name;
    private Author author;
    private List<Comment> comment;
    private List<Genre> genre;
}
