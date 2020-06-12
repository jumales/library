package com.jumales.library.rest.api.author;

import com.jumales.library.api.author.IAuthorApi;
import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.rest.api.IRestBase;
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
public class AuthorRest implements IRestBase {

    @Autowired
    protected IAuthorApi authorApi;

    @GetMapping
    public List<AuthorDTO> getAllAuthors(){
        List<AuthorDTO> dtos = new ArrayList<>();
        List<Author> authors = authorApi.findAll();
        authors.forEach(a ->
        {
            AuthorDTO dto = mapAuthorToDTO(a, StatusDTO.success());
            dtos.add(dto);
        });
        return dtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable(value = "id", required = true) Long id) {
        Author author = authorApi.findAuthorById(id);
        if(author == null){
            AuthorDTO result = new AuthorDTO.Builder(null)
                    .setStatus(StatusDTO.noContent(String.format("Author with id '%s' not found", id)))
                    .build();
            return  ResponseEntity.ok(result);
        }else{
            return ResponseEntity.ok(mapAuthorToDTO(author, StatusDTO.success()));
        }
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

        return ResponseEntity.ok(mapAuthorToDTO(createdAuthor, StatusDTO.created()));
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

        Author authorById = authorApi.findAuthorById(authorDTO.getId());
        if(authorById == null){
            authorDTO.setStatus(StatusDTO.badRequest(String.format("Author with id '%s' not found.", authorDTO.getId())));
            return ResponseEntity.badRequest().body(authorDTO);
        }

        authorById.setDayOfBirth(authorDTO.getDayOfBirth());
        authorById.setFirstName(authorDTO.getFirstName());
        authorById.setActive(authorDTO.isActive());
        authorById.setOib(authorDTO.getOib());
        authorById.setSecondName(authorDTO.getSecondName());

        authorApi.saveAuthor(authorById);

        return ResponseEntity.ok(mapAuthorToDTO(authorById, StatusDTO.success()));
    }

    // HELPER METHODS
    private Author mapDtoToAuthor(AuthorDTO dto){
        Author a = new Author();
        a.setSecondName(dto.getSecondName());
        a.setOib(dto.getOib());
        a.setActive(dto.isActive());
        a.setFirstName(dto.getFirstName());
        a.setDayOfBirth(dto.getDayOfBirth());
        return a;
    }

    private AuthorDTO mapAuthorToDTO(Author author, StatusDTO status){
         return new AuthorDTO.Builder(author.getOib())
             .setActive(author.getActive())
             .setDayOfBirth(author.getDayOfBirth())
             .setFirstName(author.getFirstName())
             .setSecondName(author.getSecondName())
             .setId(author.getAuthorId())
             .setStatus(status)
             .build();

    }
}