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
    protected Long bookAuthorId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="book_id")
    protected Book book;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="author_id")
    protected Author author;

    @Setter(AccessLevel.NONE)
    @Embedded
    protected Audit audit = new Audit();

    public void delete(){
        this.audit.setDeleted(true);
    }

}
