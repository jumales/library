package com.jumales.library.repository;

import com.jumales.library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByOibAndAuditDeleted(String oib, boolean deleted);

    List<Author> findByAuditDeleted(boolean deleted);

    Optional<Author> findByIdAndAuditDeleted(Long id, boolean deleted);

}
