package com.jumales.library.api.book;

import com.jumales.library.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    public static final String BOOK_TITLE = "FIRST_BOOK";
    public static final String BOOK_IBN = "ibn123";

    @MockBean
    protected IBookApi bookApi;

    @Test
    public void createBookTest(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(BOOK_IBN);

        given(this.bookApi.createBook(b)).willReturn(b);

    }
}
