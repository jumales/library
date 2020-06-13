package com.jumales.library.rest.api.author;

import com.jumales.library.api.author.IAuthorApi;
import com.jumales.library.api.book2author.IBookAuthorApi;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.rest.api.IRestCommon;
import com.jumales.library.rest.api.author.dto.AuthorDTO;
import com.jumales.library.rest.api.book.dto.BookDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${rest.root.url}" + "/authors")
public class AuthorRest implements IRestCommon {

    @Autowired
    protected IAuthorApi authorApi;
    @Autowired
    protected IBookAuthorApi bookAuthorApi;

    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> dtos = new ArrayList<>();
        List<Author> authors = authorApi.findAll();
        authors.forEach(a ->
        {
            AuthorDTO dto = mapAuthorToDTO(a, StatusDTO.success(), null);
            dtos.add(dto);
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable(value = "id", required = true) Long id) {
        Author author = authorApi.findAuthorById(id);
        ResponseEntity<AuthorDTO> response = isEntityNull(id, author, Author.class.getSimpleName());
        if(response != null) return response;

        List<BookAuthor> books = bookAuthorApi.findByAuthorId(id);
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

        boolean isIbnUnique = authorApi.isOibUnique(authorDTO.getOib(), authorDTO.getId());
        if(!isIbnUnique){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("Author with OIB '%s' exist!", authorDTO.getOib())));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        Author createdAuthor = authorApi.saveAuthor(mapDtoToAuthor(authorDTO));

        return ResponseEntity.ok(mapAuthorToDTO(createdAuthor, StatusDTO.created(), null));
    }

    @PutMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody(required = true) AuthorDTO authorDTO){
        String validation = AuthorDTO.validateAuthor(authorDTO, true);
        if(validation != null){
            authorDTO.setStatus(StatusDTO.badRequest(validation));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        boolean isOibUnique = authorApi.isOibUnique(authorDTO.getOib(), authorDTO.getId());
        if(!isOibUnique){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("Author with OIB '%s' exist!", authorDTO.getOib())));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        Author author = authorApi.findAuthorById(authorDTO.getId());
        ResponseEntity<AuthorDTO> response = isEntityNull(authorDTO.getId(), author, Author.class.getSimpleName());
        if(response != null) return response;

        author.setDayOfBirth(authorDTO.getDayOfBirth());
        author.setFirstName(authorDTO.getFirstName());
        author.setActive(authorDTO.isActive());
        author.setOib(authorDTO.getOib());
        author.setSecondName(authorDTO.getSecondName());

        authorApi.saveAuthor(author);

        return ResponseEntity.ok(mapAuthorToDTO(author, StatusDTO.success(), null));
    }

    @DeleteMapping(consumes = JSON_CONSUME, produces = JSON_PRODUCE)
    public ResponseEntity<AuthorDTO> deleteAuthor(@RequestBody AuthorDTO authorDTO){
        Author author = authorApi.findAuthorById(authorDTO.getId());
        ResponseEntity<AuthorDTO> response = isEntityNull(authorDTO.getId(), author, Author.class.getSimpleName());
        if(response != null) return response;

        List<BookAuthor> ba = bookAuthorApi.findByAuthorId(authorDTO.getId());
        if(!ba.isEmpty()){
            bookAuthorApi.deleteBookAuthors(ba);
        }

        authorApi.deleteAuthor(author);

        authorDTO.setStatus(StatusDTO.success(String.format("Deleted")));
        return ResponseEntity.ok(authorDTO);
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