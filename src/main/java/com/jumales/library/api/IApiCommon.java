package com.jumales.library.api;

import com.jumales.library.api.author.AuthorApi;
import com.jumales.library.entities.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Contains common things for api classes
 */
public interface IApiCommon {
    static final Logger logger = LogManager.getLogger(IApiCommon.class);

    /**
     *
     * @param className
     * @param field which is inspected
     * @param value which is inspected
     * @param opt
     * @param <T>
     * @return
     */
    default public <T> T validateIfEntityFound(String className, String field, Object value, Optional<T> opt) {
        if(opt.isPresent()){
            return opt.get();
        }else{

            logger.warn("{} with {}: '{}' not found. ", className, field, value);
            return null;
        }
    }
}
