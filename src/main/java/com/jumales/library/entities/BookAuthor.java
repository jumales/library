package com.jumales.library.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "book_author")
@Entity
@Data
public class BookAuthor extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "book_author_id")
    private Long bookAuthorId;

    @Column(name="book_id", nullable = false)
    private Long bookId;

    @Column(name="author_id", nullable = false)
    private Long authorId;
}
