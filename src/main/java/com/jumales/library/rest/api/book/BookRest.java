package com.jumales.library.rest.api.book;

import com.jumales.library.entities.Book;
import com.jumales.library.api.book.IBookApi;
import com.jumales.library.rest.api.IRestBase;
import com.jumales.library.rest.api.dto.BookDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/books")
public class BookRest implements IRestBase {
    //TODO: listen https://www.youtube.com/watch?v=996OiexHze0
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
    public ResponseEntity<BookDTO> createBook(@RequestBody(required = true) BookDTO bookDTO){
        String validation = BookDTO.validateBook(bookDTO, false);
        if(validation != null){
            bookDTO.setStatus(StatusDTO.badRequest(validation));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        if(bookDTO.getId() != null){
            bookDTO.setStatus(StatusDTO.badRequest(String.format("ID field needs to be null")));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        boolean isIbnUnique = bookApi.isIbnUnique(bookDTO.getIbn(), bookDTO.getId());
        if(!isIbnUnique){
            bookDTO.setStatus(StatusDTO.badRequest(String.format("Book with IBN '%s' exist!", bookDTO.getIbn())));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        Book createdBook = bookApi.saveBook(mapDtoToBook(bookDTO));

        return ResponseEntity.ok(mapBookToDTO(createdBook, StatusDTO.created()));
    }

    @PutMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> updateBook(@RequestBody(required = true) BookDTO bookDTO){
        String validation = BookDTO.validateBook(bookDTO, true);
        if(validation != null){
            bookDTO.setStatus(StatusDTO.badRequest(validation));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        boolean isIbnUnique = bookApi.isIbnUnique(bookDTO.getIbn(), bookDTO.getId());
        if(!isIbnUnique){
            bookDTO.setStatus(StatusDTO.badRequest(String.format("Book with IBN '%s' exist!", bookDTO.getIbn())));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        Book bookById = bookApi.findBookById(bookDTO.getId());
        if(bookById == null){
            bookDTO.setStatus(StatusDTO.badRequest(String.format("Book with id '%s' not found.", bookDTO.getId())));
            return ResponseEntity.badRequest().body(bookDTO);
        }

        bookById.setTitle(bookDTO.getTitle());
        bookById.setIbn(bookDTO.getIbn());

        bookApi.saveBook(bookById);

        return ResponseEntity.ok(mapBookToDTO(bookById, StatusDTO.success()));
    }

    // HELPER METHODS
    private Book mapDtoToBook(BookDTO dto){
        Book b = new Book();
        b.setIbn(dto.getIbn());
        b.setTitle(dto.getTitle());
        b.setBookId(dto.getId());
        return b;
    }

    private BookDTO mapBookToDTO(Book book, StatusDTO status) {
        return new BookDTO.Builder(book.getIbn())
            .setTitle(book.getTitle())
            .setId(book.getBookId())
            .setStatus(status)
            .build();
    }

}