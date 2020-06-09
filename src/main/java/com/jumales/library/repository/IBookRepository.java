package com.jumales.library.repository;

import com.jumales.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIbn(String ibn);
}
