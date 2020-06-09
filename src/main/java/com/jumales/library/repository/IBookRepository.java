package com.jumales.library.repository;

import com.jumales.library.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface IBookRepository extends CrudRepository<Book, Long> {

}
