package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"book"})
@EqualsAndHashCode(exclude = {"book"})
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;
    private Book book;
    private String comment;
}
