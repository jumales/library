package com.jumales.library.rest.api.book.dto;

import com.jumales.library.entities.Author;
import com.jumales.library.rest.api.author.dto.AuthorDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDTO{

    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.NONE)
    private String title;
    @Setter(AccessLevel.NONE)
    private String ibn;
    private StatusDTO status;
    @Setter(AccessLevel.NONE)
    private List<AuthorDTO> authors = new ArrayList<>();

    public static String validateBook(BookDTO book, boolean controlId){
        if(book.getIbn() == null || book.getIbn().isEmpty()) return "IBN can't be empty";
        if(book.getTitle() == null || book.getTitle().isEmpty()) return "Title can't be empty";
        if(controlId) if(book.getId() == null) return "Id can't be null";
        return null;
    }

    public static class Builder{

        Long id;
        private String title;
        private String ibn;
        private StatusDTO status;
        private List<AuthorDTO> authors = new ArrayList<>();

        public Builder(final String ibn){
            this.ibn = ibn;
        }

        public Builder setTitle(final String title){
            this.title = title;
            return this;
        }

        public Builder setId(final Long id){
            this.id = id;
            return this;
        }

        public Builder setStatus(final StatusDTO status){
            this.status = status;
            return this;
        }

        public Builder addAuthor(final AuthorDTO authorDTO){
            this.authors.add(authorDTO);
            return this;
        }

        public BookDTO build(){
            BookDTO dto = new BookDTO();
            dto.id = this.id;
            dto.status = this.status;
            dto.ibn = this.ibn;
            dto.title = this.title;
            dto.authors = this.authors;
            return dto;
        }
    }
}