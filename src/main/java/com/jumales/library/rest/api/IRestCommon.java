package com.jumales.library.rest.api;

import com.jumales.library.entities.Author;
import com.jumales.library.entities.Book;
import com.jumales.library.entities.BookAuthor;
import com.jumales.library.rest.api.author.dto.AuthorDTO;
import com.jumales.library.rest.api.book.dto.BookDTO;
import com.jumales.library.rest.api.dto.StatusDTO;

import java.util.List;

public interface IRestCommon {
    public static final String JSON_CONSUME = "application/json";
    public static final String JSON_PRODUCE = "application/json";

    default Book mapDtoToBook(BookDTO dto){
        Book b = new Book();
        b.setIbn(dto.getIbn());
        b.setTitle(dto.getTitle());
        b.setId(dto.getId());
        return b;
    }

    default BookDTO mapBookToDTO(Book book, StatusDTO status, List<BookAuthor> authors) {
        BookDTO.Builder builder = new BookDTO.Builder(book.getIbn())
                .setTitle(book.getTitle())
                .setId(book.getId())
                .setStatus(status);

        if(authors != null){
            authors.forEach(a -> {
                builder.addAuthor(mapAuthorToDTO(a.getAuthor(), StatusDTO.success(), null));
            });
        }
        return builder.build();
    }

    default Author mapDtoToAuthor(AuthorDTO dto){
        Author a = new Author();
        a.setLastName(dto.getLastName());
        a.setOib(dto.getOib());
        a.setActive(dto.isActive());
        a.setFirstName(dto.getFirstName());
        a.setDayOfBirth(dto.getDayOfBirth());
        return a;
    }

    default AuthorDTO mapAuthorToDTO(Author author, StatusDTO status, List<BookAuthor> books) {
        AuthorDTO.Builder builder = new AuthorDTO.Builder(author.getOib())
                .setActive(author.getActive())
                .setDayOfBirth(author.getDayOfBirth())
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setId(author.getId())
                .setStatus(status);

        if(books != null){
            books.forEach(a -> {
                builder.addBook(mapBookToDTO(a.getBook(), StatusDTO.success(), null));
            });
        }

        return builder.build();
    }
}
