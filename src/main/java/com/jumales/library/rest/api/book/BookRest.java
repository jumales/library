package com.jumales.library.rest.api.book;

import com.jumales.library.service.author.AuthorService;
import com.jumales.library.service.book2author.BookAuthorService;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.service.book.BookService;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.rest.api.RestCommon;
import com.jumales.library.rest.api.book.dto.BookDTO;
import com.jumales.library.rest.api.book2Author.dto.BookAuthorDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/books")
public class BookRest implements RestCommon {
    @Autowired
    protected BookService bookApi;
    @Autowired
    protected BookAuthorService bookAuthorApi;
    @Autowired
    protected AuthorService authorApi;

    @GetMapping
    public List<BookDTO> getAllBooks(){
        List<BookDTO> dtos = new ArrayList<>();
        List<Book> books = bookApi.findAll();
        books.forEach(b ->
        {
            List<BookAuthor> authors = bookAuthorApi.findByBookId(b.getId());
            BookDTO dto = mapBookToDTO(b, StatusDTO.success(), authors);
            dtos.add(dto);
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable(value = "id", required = true) Long id) {
        Book book = bookApi.findBookById(id);
        ResponseEntity<BookDTO> dto1 = isEntityNull(id, book, Book.class.getSimpleName());
        if (dto1 != null) return dto1;

        List<BookAuthor> authors = bookAuthorApi.findByBookId(id);

        return ResponseEntity.ok(mapBookToDTO(book, StatusDTO.success(), authors));
    }

    @PostMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> createBook(@RequestBody(required = true) BookDTO bookDTO){
        String validation = BookDTO.validateBook(bookDTO, false);
        if(validation != null){ ;
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

        return ResponseEntity.ok(mapBookToDTO(createdBook, StatusDTO.created(), null));
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
        ResponseEntity<BookDTO> dto1 = isEntityNull(bookDTO.getId(), bookById, Book.class.getSimpleName());
        if (dto1 != null) return dto1;

        bookById.setTitle(bookDTO.getTitle());
        bookById.setIbn(bookDTO.getIbn());

        bookApi.saveBook(bookById);

        return ResponseEntity.ok(mapBookToDTO(bookById, StatusDTO.success(), null));
    }

    @GetMapping("/author/{authorId}")
    public List<BookDTO> getBooksByAuthor(@PathVariable Long authorId){
        List<BookDTO> books = new ArrayList<>();
        List<BookAuthor> bookAuthor = bookAuthorApi.findByAuthorId(authorId);
        bookAuthor.forEach(ba -> {
            books.add(mapBookToDTO(ba.getBook(), StatusDTO.success(), null));
        });
        return books;
    }

    @PostMapping(value ="/addAuthorToBook", consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> addAuthorToBook(@RequestBody BookAuthorDTO bookAuthorDTO){
        Long bookId = bookAuthorDTO.getBookId();
        Long authorId = bookAuthorDTO.getAuthorId();

        Book book = bookApi.findBookById(bookId);
        ResponseEntity<BookDTO> dto1 = isEntityNull(bookId, book, Book.class.getSimpleName());
        if (dto1 != null) return dto1;

        Author author = authorApi.findAuthorById(authorId);
        ResponseEntity<BookDTO> dto = isEntityNull(authorId, author, Author.class.getSimpleName());
        if (dto != null) return dto;

        BookAuthor ba = bookAuthorApi.findByBookIdAuthorId(bookId, authorId);
        if(ba != null){
            BookDTO dto2 = new BookDTO();
            dto2.setStatus(StatusDTO.badRequest(String.format("Author with ID '%s' is added earlier to book with ID '%s'.",
                    authorId, bookId)));
            return ResponseEntity.ok(dto2);
        }

        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBook(book);
        bookAuthor.setAuthor(author);

        bookAuthorApi.saveBookAuthor(bookAuthor);
        List<BookAuthor> authors = bookAuthorApi.findByBookId(bookId);
        BookDTO bookDTO = mapBookToDTO(book, StatusDTO.success(), authors);

        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping(value = "/deleteAuthorFromBook", consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> deleteAuthorFromBook(@RequestBody BookAuthorDTO bookAuthorDTO){
        Long bookId = bookAuthorDTO.getBookId();
        Long authorId = bookAuthorDTO.getAuthorId();

        BookAuthor ba = bookAuthorApi.findByBookIdAuthorId(bookId, authorId);
        ResponseEntity<BookDTO> response = isEntityNull(null, ba, BookAuthor.class.getSimpleName());
        if(response != null) return response;

        //TODO: book needs to have one author

        bookAuthorApi.deleteBookAuthor(ba);

        Book book = bookApi.findBookById(bookId);
        ResponseEntity<BookDTO> dto1 = isEntityNull(bookId, book, Book.class.getSimpleName());
        if (dto1 != null) return dto1;

        List<BookAuthor> authors = bookAuthorApi.findByBookId(bookId);
        BookDTO bookDTO = mapBookToDTO(book, StatusDTO.success(), authors);

        return ResponseEntity.ok(bookDTO);
    }

    @DeleteMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<BookDTO> deleteBook(@RequestBody BookDTO bookDTO){
        Book book = bookApi.findBookById(bookDTO.getId());
        ResponseEntity<BookDTO> response = isEntityNull(bookDTO.getId(), book, Book.class.getSimpleName());
        if(response != null) return response;

        List<BookAuthor> ba = bookAuthorApi.findByBookId(bookDTO.getId());
        if(!ba.isEmpty()){
            bookAuthorApi.deleteBookAuthors(ba);
        }

        bookApi.deleteBook(book);

        bookDTO.setStatus(StatusDTO.success(String.format("Deleted")));
        return ResponseEntity.ok(bookDTO);
    }

    private <T> ResponseEntity<BookDTO> isEntityNull(Long id, T entity, String entityName) {
        if (entity == null) {
            BookDTO dto = new BookDTO();
            String message = id == null ? String.format("%s with provided ids doesn't exist.", entityName) :
                    String.format("%s with id '%s' doesn't exist.", entityName, id);
            dto.setStatus(StatusDTO.badRequest(message));
            return ResponseEntity.badRequest().body(dto);
        }
        return null;
    }

}