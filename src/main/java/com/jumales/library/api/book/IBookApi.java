package com.jumales.library.api.book;

import com.jumales.library.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public interface IBookApi {
    Book createBook(Book book);

    void editBook(Book book);

    Book findBookById(Long id);

    Book findBookByIbn(String ibn);

    List<Book> findAll();
}
