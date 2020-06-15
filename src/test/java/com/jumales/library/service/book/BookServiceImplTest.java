package com.jumales.library.service.book;

import com.jumales.library.BaseTest;
import com.jumales.library.entities.Book;
import com.jumales.library.service.book.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceImplTest extends BaseTest {

    @MockBean
    protected BookService bookApi;

    @Test
    public void saveBookTest(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(BOOK_IBN);

        given(this.bookApi.saveBook(b)).willReturn(b);
    }
}
