package com.jumales.library.repository;

import com.jumales.library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByOib(String oib);
}
