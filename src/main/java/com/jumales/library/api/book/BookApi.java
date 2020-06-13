package com.jumales.library.api.book;

import com.jumales.library.api.IApiCommon;
import com.jumales.library.entities.Book;
import com.jumales.library.repository.IBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookApi implements IBookApi, IApiCommon {
    public static final Logger logger = LogManager.getLogger(BookApi.class);

    @Autowired
    protected IBookRepository bookRepository;

    @Override
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public Book findBookById(Long id){
        Optional<Book> book = bookRepository.findById(id);
        return validateIfEntityFound(Book.class .getSimpleName(), "id", id, book);
    }

    @Override
    public Book findBookByIbn(String ibn){
        Optional<Book> book = bookRepository.findByIbn(ibn);
        return validateIfEntityFound(Book.class .getSimpleName(), "ibn", ibn, book);
    }

    @Override
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(Book book){
        bookRepository.delete(book);
    }

    /**
     * if we can't find IBN that it's unique
     * if we can find and ids are equals then are also unique
     * */
    @Override
    public boolean isIbnUnique(String ibn, Long id) {
        boolean result = true;
        Book bookByIbn = findBookByIbn(ibn);
        if (bookByIbn != null) {
            if (bookByIbn == null || !bookByIbn.getId().equals(id)) {
                result = false;
            }
        }
        return result;
    }
}
