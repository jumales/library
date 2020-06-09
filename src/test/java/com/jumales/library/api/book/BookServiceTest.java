package com.jumales.library.api.book;

import com.jumales.library.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BookServiceTest {
    //TODO: doesn't work
    public static final String BOOK_TITLE = "FIRST_BOOK";
    public static final String BOOK_IBN = "ibn123";

    @Autowired
    protected IBookApi bookApi;

    @Test
    public void createBookTest(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(BOOK_IBN);

        bookApi.createBook(b);

    }
}
