package com.jumales.library.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "book_author")
@Entity
@Data
public class BookAuthor{

    @Id
    @GeneratedValue
    @Column(name = "book_author_id")
    public Long bookAuthorId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="book_id")
    public Book book;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="author_id")
    public Author author;

    @Setter(AccessLevel.NONE)
    @Embedded
    public Audit audit = new Audit();

}
