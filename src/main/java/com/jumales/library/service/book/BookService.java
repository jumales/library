package com.jumales.library.service.book;

import com.jumales.library.entities.Book;

import java.util.List;

public interface BookService {
    Book saveBook(Book book);

    Book findBookById(Long id);

    Book findBookByIbn(String ibn);

    boolean isIbnUnique(String ibn, Long id);

    List<Book> findAll();

    void deleteBook(Book book);
}
