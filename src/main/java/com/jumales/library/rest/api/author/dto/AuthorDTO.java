package com.jumales.library.rest.api.author.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private Date dayOfBirth;
    private String oib;
    private boolean active;

    /**
     * validate author dto
     * @param author
     * @param controlId
     * @return
     */
    public static String validateAuthor(AuthorDTO author, boolean controlId){
        if(author.getOib() == null || author.getOib().isEmpty()) return "Author can't be empty";
        if(author.getFirstName() == null || author.getFirstName().isEmpty()) return "First name can't be empty";
        if(author.getLastName() == null || author.getLastName().isEmpty()) return "Last name can't be empty";
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
        private Date dayOfBirth;
        private String oib;
        private boolean active;

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

        public  Builder setLastName(final String flastName){
            this.lastName = lastName;
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

        public AuthorDTO build() {
            AuthorDTO dto = new AuthorDTO();
            dto.id = this.id;
            dto.firstName = this.firstName;
            dto.lastName = this.lastName;
            dto.dayOfBirth = this.dayOfBirth;
            dto.oib = this.oib;
            dto.active = this.active;

            return dto;
        }
    }
}