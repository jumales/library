package com.jumales.library.helper;

import com.jumales.library.api.author.IAuthorApi;
import com.jumales.library.api.book.IBookApi;
import com.jumales.library.api.book2author.IBookAuthorApi;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.entities.BookAuthor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Setup development database
 */

@Component
@Profile("dev")
public class DatabaseInitialization {
    private static final Logger logger = LogManager.getLogger(DatabaseInitialization.class);

    @Autowired
    private IBookApi bookApi;

    @Autowired
    private IAuthorApi authorApi;

    @Autowired
    private IBookAuthorApi bookAuthorApi;

    @EventListener(ApplicationReadyEvent.class)
    public void initDevData() {
        Book b1 = new Book();
        b1.setIbn("1111");
        b1.setTitle("First book");
        b1 = bookApi.saveBook(b1);
        logger.info("Created: {}", b1);

        Book b2 = new Book();
        b2.setIbn("2222");
        b2.setTitle("Second book");
        b2 = bookApi.saveBook(b2);
        logger.info("Created: {}", b2);

        Author a1 = new Author();
        a1.setSecondName("Maleš");
        a1.setOib("1234");
        a1.setActive(true);
        a1.setFirstName("Jure");
        Calendar cal = Calendar.getInstance();
        cal.set(1987, 11, 23);
        a1.setDayOfBirth(cal.getTime());
        a1 = authorApi.saveAuthor(a1);
        logger.info("Created: {}", a1);

        Author a2 = new Author();
        a2.setSecondName("Antolić");
        a2.setOib("5678");
        a2.setActive(true);
        a2.setFirstName("Nikolina");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(1991, 11, 15);
        a2.setDayOfBirth(cal2.getTime());
        a2 = authorApi.saveAuthor(a2);
        logger.info("Created: {}", a2);

        Author a3 = new Author();
        a3.setSecondName("Treći");
        a3.setOib("12345678");
        a3.setActive(true);
        a3.setFirstName("Netko");
        a3.setDayOfBirth(cal2.getTime());
        a3 = authorApi.saveAuthor(a3);
        logger.info("Created: {}", a3);

        BookAuthor ba1 = new BookAuthor();
        ba1.setAuthor(a1);
        ba1.setBook(b1);
        ba1 = bookAuthorApi.saveBookAuthor(ba1);
        logger.info("Created: {}", ba1);

        BookAuthor ba2 = new BookAuthor();
        ba2.setAuthor(a1);
        ba2.setBook(b2);
        ba2 = bookAuthorApi.saveBookAuthor(ba2);
        logger.info("Created: {}", ba2);

        logger.info(bookAuthorApi.findById(ba2.getBookAuthorId()));
    }
}