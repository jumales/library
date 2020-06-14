package com.jumales.library.repository;

import com.jumales.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIbnAndAuditDeleted(String ibn, boolean deleted);

    List<Book> findByAuditDeleted(boolean deleted);

    Optional<Book> findByIdAndAuditDeleted(Long id, boolean deleted);
}
