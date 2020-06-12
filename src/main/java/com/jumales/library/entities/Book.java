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
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    private String title;

    @Column(nullable = false, unique = true)
    private String ibn;

    @Setter(AccessLevel.NONE)
    @Embedded
    private Audit audit = new Audit();
}
