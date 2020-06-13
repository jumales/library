package com.jumales.library.api.book2author;

import com.jumales.library.entities.BookAuthor;

import java.util.List;

public interface IBookAuthorApi {
    BookAuthor saveBookAuthor(BookAuthor bookAuthor);

    BookAuthor findById(Long id);

    List<BookAuthor> findByBookId(Long bookId);

    List<BookAuthor> findByAuthorId(Long authorId);

    BookAuthor findByBookIdAuthorId(Long bookId, Long authorId);

    void deleteBookAuthor(BookAuthor bookAuthor);

    void deleteBookAuthors(Iterable<BookAuthor> bas);
}
