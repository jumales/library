package com.jumales.library.repository;

import com.jumales.library.entities.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
