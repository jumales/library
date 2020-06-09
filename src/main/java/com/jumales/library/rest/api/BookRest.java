package com.jumales.library.rest.api;

import com.jumales.library.entities.Book;
import com.jumales.library.api.book.IBookApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/book")
public class BookRest {
    //TODO: listen https://www.youtube.com/watch?v=996OiexHze0
    //TODO: https://dzone.com/articles/applying-hateoas-to-a-rest-api-with-spring-boot
    @Autowired
    protected IBookApi bookService;

    //TODO: use ResponseEntity and validate if book exists https://dzone.com/articles/applying-hateoas-to-a-rest-api-with-spring-boot
    @GetMapping("/{id}")
    public Book getById(@PathVariable(value = "id") Long id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookService.findAll();
    }
}