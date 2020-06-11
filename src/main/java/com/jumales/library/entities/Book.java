package com.jumales.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jumales.library.rest.api.dto.BookDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
//@EqualsAndHashCode(callSuper = false)
//@ToString(callSuper = true)
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name = "book_seq", initialValue = 1, allocationSize = 1)
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
    @JsonIgnore
    Set<Author> authors = new HashSet<>();

    @Embedded
    private Audit audit = new Audit();
}
