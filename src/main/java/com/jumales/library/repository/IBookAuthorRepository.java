package com.jumales.library.repository;

import com.jumales.library.entities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    List<BookAuthor> findByBookId(Long bookId);

    List<BookAuthor> findByAuthorId(Long authorId);

    BookAuthor findByBookIdAndAuthorId(Long bookId, Long authorId);
}
