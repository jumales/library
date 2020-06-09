package com.jumales.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
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
    @JsonIgnore
    Set<Author> authors = new HashSet<>();
}
