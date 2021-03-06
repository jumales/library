package com.jumales.library.service.author;

import com.jumales.library.BaseTest;
import com.jumales.library.entities.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceImplTest extends BaseTest {

    @MockBean
    protected AuthorService authorApi;

    @Test
    public void saveAuthorTest(){
        Author a = new Author();
        a.setActive(true);
        a.setDayOfBirth(getDate(1987, 11, 23));
        a.setFirstName(AUTHOR_FIRST_NAME);
        a.setLastName(AUTHOR_LAST_NAME);
        a.setOib(AUTHOR_OIB);

        given(this.authorApi.saveAuthor(a)).willReturn(a);
    }

    @Test
    public void saveNonUniqueOibTest(){
        saveAuthorTest();
        Author a = new Author();
        a.setActive(true);
        a.setDayOfBirth(getDate(1987, 11, 23));
        a.setFirstName(AUTHOR_FIRST_NAME);
        a.setLastName(AUTHOR_LAST_NAME);
        a.setOib(AUTHOR_OIB);

        given(this.authorApi.saveAuthor(a)).willReturn(null);
    }
}