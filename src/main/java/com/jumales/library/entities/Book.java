package com.jumales.library.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
@EqualsAndHashCode(callSuper = false)
public class Book extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long bookId;

    private String title;

    @Column(nullable = false)
    private String ibn;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = { @JoinColumn(name = "book_id")},
        inverseJoinColumns = { @JoinColumn(name = "author_id")}
    )
    Set<Author> authors = new HashSet<>();
}
