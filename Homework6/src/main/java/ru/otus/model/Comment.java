package ru.otus.model;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "comments")
@ToString(exclude = {"book"})
@EqualsAndHashCode(exclude = {"book"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Book book;
    private String comment;
}
