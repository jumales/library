package com.jumales.library.helper;

import com.jumales.library.service.author.AuthorService;
import com.jumales.library.service.book.BookService;
import com.jumales.library.service.book2author.BookAuthorService;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.entities.User;
import com.jumales.library.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Setup development database
 */

@Component
@Profile("dev")
public class DatabaseInitialization {
    private static final Logger logger = LogManager.getLogger(DatabaseInitialization.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookAuthorService bookAuthorService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initDevData() {
        Book b1 = new Book();
        b1.setIbn("1111");
        b1.setTitle("First book");
        b1 = bookService.saveBook(b1);
        logger.info("Created: {}", b1);

        Book b2 = new Book();
        b2.setIbn("2222");
        b2.setTitle("Second book");
        b2 = bookService.saveBook(b2);
        logger.info("Created: {}", b2);

        Author a1 = new Author();
        a1.setLastName("Maleš");
        a1.setOib("1234");
        a1.setActive(true);
        a1.setFirstName("Jure");
        Calendar cal = Calendar.getInstance();
        cal.set(1987, Calendar.NOVEMBER, 23);
        a1.setDayOfBirth(cal);
        a1 = authorService.saveAuthor(a1);
        logger.info("Created: {}", a1);

        Author a2 = new Author();
        a2.setLastName("Antolić");
        a2.setOib("5678");
        a2.setActive(true);
        a2.setFirstName("Nikolina");
        Calendar cal2 = Calendar.getInstance();
        cal2.set(1991, Calendar.NOVEMBER, 15);
        a2.setDayOfBirth(cal2);
        a2 = authorService.saveAuthor(a2);
        logger.info("Created: {}", a2);

        Author a3 = new Author();
        a3.setLastName("Treći");
        a3.setOib("12345678");
        a3.setActive(true);
        a3.setFirstName("Netko");
        a3.setDayOfBirth(cal2);
        a3 = authorService.saveAuthor(a3);
        logger.info("Created: {}", a3);

        BookAuthor ba1 = new BookAuthor();
        ba1.setAuthor(a1);
        ba1.setBook(b1);
        ba1 = bookAuthorService.saveBookAuthor(ba1);
        logger.info("Created: {}", ba1);

        BookAuthor ba2 = new BookAuthor();
        ba2.setAuthor(a1);
        ba2.setBook(b2);
        ba2 = bookAuthorService.saveBookAuthor(ba2);
        logger.info("Created: {}", ba2);

        logger.info(bookAuthorService.findById(ba2.getBookAuthorId()));

        addUsers();
    }

    private void addUsers(){

        this.userRepository.save(User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER"))
                .build()
        );

        this.userRepository.save(User.builder()
                .username("admin")
                .password(this.passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build()
        );

        logger.debug("printing all users...");
        this.userRepository.findAll().forEach(v -> logger.debug(String.format("%s", v)));
    }
}