package com.jumales.library;

import com.jumales.library.repository.IBookRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestAbstract {

    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected IBookRepository bookRepository;
}
