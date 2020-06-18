package com.jumales.library.rest.api.author.dto;

import com.jumales.library.rest.api.book.dto.BookDTO;
import com.jumales.library.rest.api.dto.StatusDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class AuthorDTO {

    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.NONE)
    private String firstName;
    @Setter(AccessLevel.NONE)
    private String lastName;
    @Setter(AccessLevel.NONE)
    private String fullName;
    @Setter(AccessLevel.NONE)
    private Calendar dayOfBirth;
    @Setter(AccessLevel.NONE)
    private String oib;
    @Setter(AccessLevel.NONE)
    private boolean active;
    private StatusDTO status;
    @Setter(AccessLevel.NONE)
    private List<BookDTO> books = new ArrayList<>();

    /**
     * validate author dto
     * @param author
     * @param controlId
     * @return
     */
    public static String validateAuthor(AuthorDTO author, boolean controlId){
        if(author.getOib() == null || author.getOib().isEmpty()) return "Author OIB can't be empty";
        if(author.getFirstName() == null || author.getFirstName().isEmpty()) return "First name can't be empty";
        if(author.getLastName() == null || author.getLastName().isEmpty()) return "Second name can't be empty";
        if(author.getDayOfBirth() == null) return "Day of birth can't be empty";
        if(controlId) if(author.getId() == null) return "Id can't be null";
        return null;
    }

    public String getFullName(){
        return String.format("%s %s", firstName, lastName);
    }

    public static class Builder{

        private Long id;
        private String firstName;
        private String lastName;
        private Calendar dayOfBirth;
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

        public  Builder setLastName(final String lastName){
            this.lastName = lastName;
            return this;
        }

        public  Builder setDayOfBirth(final Calendar dayOfBirth){
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
            dto.lastName = this.lastName;
            dto.dayOfBirth = this.dayOfBirth;
            dto.oib = this.oib;
            dto.active = this.active;
            dto.status = this.status;
            dto.books = this.books;
            return dto;
        }
    }
}