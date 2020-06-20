package com.jumales.library.rest.api.author;

import com.jumales.library.entities.Author;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.rest.api.RestCommon;
import com.jumales.library.rest.api.author.dto.AuthorDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import com.jumales.library.service.author.AuthorService;
import com.jumales.library.service.book2author.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/authors")
public class AuthorRest implements RestCommon {

    @Autowired
    protected AuthorService authorService;
    @Autowired
    protected BookAuthorService bookAuthorService;

    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> dtos = new ArrayList<>();
        List<Author> authors = authorService.findAll();
        authors.forEach(a ->
        {
            List<BookAuthor> books = bookAuthorService.findByAuthorId(a.getId());
            AuthorDTO dto = mapAuthorToDTO(a, StatusDTO.success(), books);
            dtos.add(dto);
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable(value = "id", required = true) Long id) {
        Author author = authorService.findAuthorById(id);
        ResponseEntity<AuthorDTO> response = isEntityNull(id, author, Author.class.getSimpleName());
        if(response != null) return response;

        List<BookAuthor> books = bookAuthorService.findByAuthorId(id);
        return ResponseEntity.ok(mapAuthorToDTO(author, StatusDTO.success(), books));
    }

    @PostMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody(required = true) AuthorDTO authorDTO){
        String validation = AuthorDTO.validateAuthor(authorDTO, false);
        if(validation != null){
            authorDTO.setStatus(StatusDTO.badRequest(validation));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        if(authorDTO.getId() != null){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("ID field needs to be null")));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        boolean isIbnUnique = authorService.isOibUnique(authorDTO.getOib(), authorDTO.getId());
        if(!isIbnUnique){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("Author with OIB '%s' exist!", authorDTO.getOib())));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        Author createdAuthor = authorService.saveAuthor(mapDtoToAuthor(authorDTO));

        return ResponseEntity.ok(mapAuthorToDTO(createdAuthor, StatusDTO.created(), null));
    }

    @PutMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody(required = true) AuthorDTO authorDTO){
        String validation = AuthorDTO.validateAuthor(authorDTO, true);
        if(validation != null){
            authorDTO.setStatus(StatusDTO.badRequest(validation));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        boolean isOibUnique = authorService.isOibUnique(authorDTO.getOib(), authorDTO.getId());
        if(!isOibUnique){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("Author with OIB '%s' exist!", authorDTO.getOib())));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        Author author = authorService.findAuthorById(authorDTO.getId());
        ResponseEntity<AuthorDTO> response = isEntityNull(authorDTO.getId(), author, Author.class.getSimpleName());
        if(response != null) return response;

        author.setDayOfBirth(authorDTO.getDayOfBirth());
        author.setFirstName(authorDTO.getFirstName());
        author.setActive(authorDTO.isActive());
        author.setOib(authorDTO.getOib());
        author.setLastName(authorDTO.getLastName());

        authorService.saveAuthor(author);

        return ResponseEntity.ok(mapAuthorToDTO(author, StatusDTO.success(), null));
    }

    @DeleteMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<AuthorDTO> deleteAuthor(@RequestBody AuthorDTO authorDTO){
        Author author = authorService.findAuthorById(authorDTO.getId());
        ResponseEntity<AuthorDTO> response = isEntityNull(authorDTO.getId(), author, Author.class.getSimpleName());
        if(response != null) return response;

        List<BookAuthor> ba = bookAuthorService.findByAuthorId(authorDTO.getId());
        if(!ba.isEmpty()){
            bookAuthorService.deleteBookAuthors(ba);
        }

        authorService.deleteAuthor(author);

        authorDTO.setStatus(StatusDTO.success(String.format("Deleted")));
        return ResponseEntity.ok(authorDTO);
    }
    @GetMapping("/notOnBook/{bookId}")
    public List<AuthorDTO> findAuthorWhichNotOnBook(@PathVariable Long bookId){
        List<Author> authors = authorService.findAll();
        List<BookAuthor> ba = bookAuthorService.findByBookId(bookId);
        List<Author> authorsOnBook = new ArrayList<>();
        ba.forEach(b ->
            authorsOnBook.add(b.getAuthor())
        );
        authors.removeIf(a -> authorsOnBook.contains(a));
        List<AuthorDTO> result = new ArrayList<>();
        authors.forEach(a ->{
            result.add(mapAuthorToDTO(a, StatusDTO.success(), null));
        });
        return result;
    }

    private <T> ResponseEntity<AuthorDTO> isEntityNull(Long id, T entity, String entityName) {
        if (entity == null) {
            AuthorDTO dto = new AuthorDTO();
            String message = id == null ? String.format("%s with provided ids doesn't exist.", entityName) :
                    String.format("%s with id '%s' doesn't exist.", entityName, id);
            dto.setStatus(StatusDTO.badRequest(message));
            return ResponseEntity.badRequest().body(dto);
        }
        return null;
    }

}