package com.jumales.library.repository;

import com.jumales.library.TestAbstract;
import com.jumales.library.entities.Book;
import org.junit.Assert;
import org.junit.Test;

public class IBookRepositoryTest  extends TestAbstract {

    public static final String BOOK_TITLE = "FIRST_BOOK";
    public static final String IBN = "1234";

    @Test
    public void testSaveBook(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(IBN);

        bookRepository.save(b);

        Book bookByIbn = bookRepository.findByIbn(IBN);

        Assert.assertEquals(IBN, bookByIbn.getIbn());
        Assert.assertEquals(BOOK_TITLE, bookByIbn.getTitle());


    }
}