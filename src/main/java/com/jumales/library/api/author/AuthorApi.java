package com.jumales.library.api.author;

import com.jumales.library.api.IApiCommon;
import com.jumales.library.entities.Author;
import com.jumales.library.repository.IAuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorApi implements IAuthorApi, IApiCommon {
    public static final Logger logger = LogManager.getLogger(AuthorApi.class);

    @Autowired
    protected IAuthorRepository authorRepository;

    /**
     * save author
     * @param author
     * @return
     */
    @Override
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    /**
     * find author by Id
     * if not found - return null
     * @param id
     * @return
     */
    @Override
    public Author findAuthorById(Long id){
        Optional<Author> author = authorRepository.findById(id);
        return validateIfEntityFound(Author.class .getSimpleName(), "id", id, author);
    }

    /**
     * find author by oib
     * if not found - return null
     * @param oib
     * @return
     */
    @Override
    public Author findAuthorByOib(String oib){
        Optional<Author> author = authorRepository.findByOib(oib);
        return validateIfEntityFound(Author.class .getSimpleName(), "oib", oib, author);
    }

    /**
     * find all authors
     * @return
     */
    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    /**
     * test is oib unique
     * if we can't find OIB that it's unique
     * if we can find and ids are equals then are also unique
     * @param oib
     * @param id
     * @return
     */
    @Override
    public boolean isOibUnique(String oib, Long id) {
        boolean result = true;
        Author authorByOib = findAuthorByOib(oib);
        if (authorByOib != null) {
            if (authorByOib == null || !authorByOib.getAuthorId().equals(id)) {
                result = false;
            }
        }
        return result;
    }
}