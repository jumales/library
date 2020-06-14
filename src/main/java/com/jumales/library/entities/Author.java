package com.jumales.library.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Author{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "second_name")
    private String secondName;

    @Column(nullable = false, name = "day_of_birth")
    private Date dayOfBirth;

    @Column(nullable = false, name = "oib", unique = true)
    private String oib;

    private Boolean active;

    @Setter(AccessLevel.NONE)
    @Embedded
    private Audit audit = new Audit();

    public void delete(){
        this.audit.setDeleted(true);
    }
}
