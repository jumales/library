package com.jumales.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "book_id")
    private Long bookId;

    private String title;

    @Column(nullable = false, unique = true)
    private String ibn;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = { @JoinColumn(name = "book_id")},
        inverseJoinColumns = { @JoinColumn(name = "author_id")}
    )
    @JsonIgnore
    Set<Author> authors = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @Embedded
    private Audit audit = new Audit();
}
