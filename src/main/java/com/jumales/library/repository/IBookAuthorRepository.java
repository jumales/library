package com.jumales.library.repository;

import com.jumales.library.entities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findByBookIdAndAuditDeleted(Long bookId, boolean deleted);

    List<BookAuthor> findByAuthorIdAndAuditDeleted(Long authorId, boolean deleted);

    BookAuthor findByBookIdAndAuthorIdAndAuditDeleted(Long bookId, Long authorId, boolean deleted);
}
