package com.jumales.library;

import com.jumales.library.enums.Profiles;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Data
public abstract class BaseTest {

    public static final Logger logger = LogManager.getLogger(BaseTest.class);

    public static final String BOOK_TITLE = "FIRST_BOOK";
    public static final String BOOK_IBN = "1234";

    @Autowired
    private Environment env;

    protected final void isActiveProd(){
        String activeProfile = env.getProperty("spring.profiles.active");
        if(Profiles.prod.name().equals(activeProfile)){
            String message = "Can't use production profile for testing!";
            logger.error(message);
            Assert.fail(message);
        }
    }

    @PostConstruct
    private void validatePrerequesites(){
        isActiveProd();
    }
}