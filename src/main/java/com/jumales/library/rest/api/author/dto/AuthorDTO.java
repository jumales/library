package com.jumales.library.rest.api.author.dto;

import com.jumales.library.rest.api.book.dto.BookDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AuthorDTO {

    private Long id;
    private String firstName;
    private String secondName;
    private String fullName;
    private Date dayOfBirth;
    private String oib;
    private boolean active;
    private StatusDTO status;
    private List<BookDTO> books = new ArrayList<>();

    /**
     * validate author dto
     * @param author
     * @param controlId
     * @return
     */
    public static String validateAuthor(AuthorDTO author, boolean controlId){
        if(author.getOib() == null || author.getOib().isEmpty()) return "Author can't be empty";
        if(author.getFirstName() == null || author.getFirstName().isEmpty()) return "First name can't be empty";
        if(author.getSecondName() == null || author.getSecondName().isEmpty()) return "Second name can't be empty";
        if(author.getDayOfBirth() == null) return "Day of birth can't be empty";
        if(controlId) if(author.getId() == null) return "Id can't be null";
        return null;
    }

    public String getFullName(){
        return String.format("%s %s", firstName, secondName);
    }

    public static class Builder{

        private Long id;
        private String firstName;
        private String secondName;
        private Date dayOfBirth;
        private String oib;
        private boolean active;
        private StatusDTO status;
        private List<BookDTO> books = new ArrayList<>();

        public Builder(final String oib){
            this.oib = oib;
        }

        public Builder setId(final Long id){
            this.id = id;
            return this;
        }

        public  Builder setFirstName(final String firstName){
            this.firstName = firstName;
            return this;
        }

        public  Builder setSecondName(final String secondName){
            this.secondName = secondName;
            return this;
        }

        public  Builder setDayOfBirth(final Date dayOfBirth){
            this.dayOfBirth = dayOfBirth;
            return this;
        }

        public  Builder setActive(final boolean active){
            this.active = active;
            return this;
        }

        public Builder setStatus(final StatusDTO status){
            this.status = status;
            return this;
        }

        public Builder addBook(final BookDTO book){
            this.books.add(book);
            return this;
        }

        public AuthorDTO build() {
            AuthorDTO dto = new AuthorDTO();
            dto.id = this.id;
            dto.firstName = this.firstName;
            dto.secondName = this.secondName;
            dto.dayOfBirth = this.dayOfBirth;
            dto.oib = this.oib;
            dto.active = this.active;
            dto.status = this.status;
            dto.books = this.books;
            return dto;
        }
    }
}