package com.jumales.library.repository;

import com.jumales.library.BaseTest;
import com.jumales.library.entities.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest extends BaseTest {

    @Autowired
    protected IBookRepository bookRepository;

    @Test
    public void testSaveBook(){
        Book b = new Book();
        b.setTitle(BOOK_TITLE);
        b.setIbn(BOOK_IBN);

        bookRepository.save(b);

        Optional<Book> bookByIbn = bookRepository.findByIbn(BOOK_IBN);
        if(!bookByIbn.isPresent()){
            Assert.fail(String.format("Test with IBN '%s' not found", BOOK_IBN));
        }
        Assert.assertEquals(BOOK_IBN, bookByIbn.get().getIbn());
        Assert.assertEquals(BOOK_TITLE, bookByIbn.get().getTitle());

    }
}