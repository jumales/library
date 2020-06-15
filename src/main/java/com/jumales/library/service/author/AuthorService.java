package com.jumales.library.service.author;

import com.jumales.library.entities.Author;

import java.util.List;

public interface AuthorService {
    Author saveAuthor(Author author);

    Author findAuthorById(Long id);

    Author findAuthorByOib(String oib);

    List<Author> findAll();

    boolean isOibUnique(String oib, Long id);

    void deleteAuthor(Author author);
}