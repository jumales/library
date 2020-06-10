package com.jumales.library.rest.api.book;

import com.jumales.library.entities.Book;
import com.jumales.library.api.book.IBookApi;
import com.jumales.library.rest.api.IRestBase;
import com.jumales.library.rest.api.entity.BookDTO;
import com.jumales.library.rest.api.entity.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/books")
public class BookRest implements IRestBase {
    //TODO: listen https://www.youtube.com/watch?v=996OiexHze0
    //TODO: https://dzone.com/articles/applying-hateoas-to-a-rest-api-with-spring-boot
    @Autowired
    protected IBookApi bookApi;

    @GetMapping
    public List<BookDTO> getAllBooks(){
        List<BookDTO> dtos = new ArrayList<>();
        List<Book> books = bookApi.findAll();
        books.forEach(b ->
        {
            BookDTO dto = mapBookToDTO(b, StatusDTO.success());
            dtos.add(dto);
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable(value = "id", required = true) Long id) {
        Book book = bookApi.findBookById(id);
        if(book == null){
            BookDTO result = new BookDTO.Builder(null)
                    .setStatus(StatusDTO.noContent(String.format("Book with id '%s' not found", id)))
                    .build();
            return  ResponseEntity.ok(result);
        }else{
            return ResponseEntity.ok(mapBookToDTO(book, StatusDTO.success()));
        }
    }

    @PostMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> createBook(@RequestBody(required = true) Book book){
        /*if(book == null) {
            BookDTO result = new BookDTO.Builder(null)
                    .setStatus(StatusDTO.badRequest("Parameter is null"))
                    .build();
            return ResponseEntity.badRequest().body(result);
        }*/

        if(book.getBookId() != null){
            BookDTO result = mapBookToDTO(book,
                    StatusDTO.badRequest(String.format("ID field needs to be null")));
            return ResponseEntity.badRequest().body(result);
        }

        Book bookByIbn = bookApi.findBookByIbn(book.getIbn());
        if(bookByIbn != null){
            BookDTO result = mapBookToDTO(book,
                    StatusDTO.badRequest(String.format("Book with IBN '%s' exist!", book.getIbn())));
            return ResponseEntity.badRequest().body(result);
        }

        Book createdBook = bookApi.createBook(book);

        return ResponseEntity.ok(mapBookToDTO(createdBook, StatusDTO.created()));
    }

    // HELPER METHODS


    private BookDTO mapBookToDTO(Book book, StatusDTO status) {
        return new BookDTO.Builder(book.getIbn())
            .setTitle(book.getTitle())
            .setId(book.getBookId())
            .setStatus(status)
            .build();
    }

}