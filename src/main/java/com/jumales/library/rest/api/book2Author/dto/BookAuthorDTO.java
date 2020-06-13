package com.jumales.library.rest.api.book2Author.dto;

import com.jumales.library.rest.api.author.dto.AuthorDTO;
import com.jumales.library.rest.api.book.dto.BookDTO;
import lombok.Data;

@Data
public class BookAuthorDTO {
    private Long bookId;
    private Long authorId;

    public BookAuthorDTO(Long bookId, Long authorId){
        this.bookId = bookId;
        this.authorId = authorId;
    }
}