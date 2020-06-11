package com.jumales.library.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Author{

    @Id
    @GeneratedValue
    @Column(name = "author_id")
    private Long authorId;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "second_name")
    private String secondName;

    @Column(nullable = false, name = "day_of_birth")
    private Date dayOfBirth;

    @Column(nullable = false, name = "oib", unique = true)
    private String oib;

    private Boolean active;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
