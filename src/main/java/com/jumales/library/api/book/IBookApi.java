package com.jumales.library.api.book;

import com.jumales.library.entities.Book;

import java.util.List;

public interface IBookApi {
    Book saveBook(Book book);

    Book findBookById(Long id);

    Book findBookByIbn(String ibn);

    boolean isIbnUnique(String ibn, Long id);

    List<Book> findAll();
}
