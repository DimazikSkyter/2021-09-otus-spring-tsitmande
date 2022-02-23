package ru.otus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;
    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Book book;
    private String comment;
}
