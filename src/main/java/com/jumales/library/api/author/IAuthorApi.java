package com.jumales.library.api.author;

import com.jumales.library.entities.Author;

import java.util.List;

public interface IAuthorApi {
    Author saveAuthor(Author author);

    Author findAuthorById(Long id);

    Author findAuthorByOib(String oib);

    List<Author> findAll();

    boolean isOibUnique(String oib, Long id);

    void deleteAuthor(Author author);
}