package com.jumales.library.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Author{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "lastName")
    private String lastName;

    @Column(nullable = false, name = "day_of_birth")
    private Calendar dayOfBirth;

    @Column(nullable = false, name = "oib")
    private String oib;

    private Boolean active;

    @Setter(AccessLevel.NONE)
    @Embedded
    private Audit audit = new Audit();

    public void delete(){
        this.audit.setDeleted(true);
    }
}
