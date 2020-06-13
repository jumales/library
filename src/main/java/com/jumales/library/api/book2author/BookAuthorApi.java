package com.jumales.library.api.book2author;

import com.jumales.library.api.IApiCommon;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.repository.IBookAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookAuthorApi implements IBookAuthorApi, IApiCommon {

    @Autowired
    protected IBookAuthorRepository bookAuthorRepository;

    /**
     * save relation beetwe book and author
     * @param bookAuthor
     * @return
     */
    @Override
    public BookAuthor saveBookAuthor(final BookAuthor bookAuthor){
        return bookAuthorRepository.save(bookAuthor);
    }

    /**
     *find by id
     * @param id
     * @return
     */
    @Override
    public BookAuthor findById(final Long id){
        Optional<BookAuthor> bookAuthor = bookAuthorRepository.findById(id);
        return validateIfEntityFound(BookAuthor.class.getSimpleName(), "id", id, bookAuthor);
    }

    /**
     * find by bookId
     * @param bookId
     * @return
     */
    @Override
    public List<BookAuthor> findByBookId(final Long bookId){
        return bookAuthorRepository.findByBookId(bookId);
    }

    /**
     * find by authorId
     * @param authorId
     * @return
     */
    @Override
    public List<BookAuthor> findByAuthorId(final Long authorId){
        return bookAuthorRepository.findByAuthorId(authorId);
    }

    /**
     * find by book and author
     * @param bookId
     * @param authorId
     * @return
     */
    @Override
    public BookAuthor findByBookIdAuthorId(Long bookId, Long authorId) {
        return bookAuthorRepository.findByBookIdAndAuthorId(bookId, authorId);
    }

    @Override
    public void deleteBookAuthors(Iterable<BookAuthor> bas){
        bookAuthorRepository.deleteAll(bas);
    }

    @Override
    public void deleteBookAuthor(BookAuthor bookAuthor){
        bookAuthorRepository.delete(bookAuthor);
    }

}