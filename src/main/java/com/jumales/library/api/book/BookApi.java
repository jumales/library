package com.jumales.library.api.book;

import com.jumales.library.entities.Book;
import com.jumales.library.repository.IBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookApi implements IBookApi {

    public static final Logger logger = LogManager.getLogger(BookApi.class);

    @Autowired
    protected IBookRepository bookRepository;

    @Override
    public Book createBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public void editBook(Book book){
        bookRepository.save(book);
    }

    @Override
    public Book findBookById(Long id){
        Optional<Book> book = bookRepository.findById(id);
        return validateIfBookFound("id", id, book);
    }

    @Override
    public Book findBookByIbn(String ibn){
        Optional<Book> book = bookRepository.findByIbn(ibn);
        return validateIfBookFound("ibn", ibn, book);
    }

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    //==== VALIDATOR =====//

    private Book validateIfBookFound(String field, Object value, Optional<Book> book) {
        if(book.isPresent()){
            return book.get();
        }else{
            logger.warn("Book with {}: '{}' not found. ", field, value);
            return null;
        }
    }
}
