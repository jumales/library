package com.jumales.library.repository;

import com.jumales.library.TestAbstract;
import com.jumales.library.entities.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class BookRepositoryTest extends TestAbstract {

    public static final Logger logger = LogManager.getLogger(BookRepositoryTest.class);

    public static final String BOOK_TITLE = "FIRST_BOOK";
    public static final String IBN = "1234";

    @Test
    public void testSaveBook(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(IBN);

        Book savedBook = bookRepository.save(b);
        logger.info("Book created: '{}'", savedBook);

        Book bookByIbn = bookRepository.findByIbn(IBN);

        Assert.assertEquals(IBN, bookByIbn.getIbn());
        Assert.assertEquals(BOOK_TITLE, bookByIbn.getTitle());


    }
}