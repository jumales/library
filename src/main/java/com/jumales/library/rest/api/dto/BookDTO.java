package com.jumales.library.rest.api.dto;

import lombok.Data;

@Data
public class BookDTO{

    private Long id;
    private String title;
    private String ibn;
    private StatusDTO status = null;

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
        private StatusDTO status= null;

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

        public BookDTO build(){
            BookDTO dto = new BookDTO();
            dto.id = this.id;
            dto.status = this.status;
            dto.ibn = this.ibn;
            dto.title = this.title;
            return dto;
        }
    }
}