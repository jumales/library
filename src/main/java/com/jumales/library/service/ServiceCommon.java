package com.jumales.library.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Contains common things for service classes
 */
public interface ServiceCommon {
    static final Logger logger = LogManager.getLogger(ServiceCommon.class);

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
