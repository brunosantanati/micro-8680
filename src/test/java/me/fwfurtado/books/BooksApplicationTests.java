package me.fwfurtado.books;

import me.fwfurtado.books.rest.BookFeignClient;
import me.fwfurtado.books.rest.BookRestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksApplicationTests {

    @Autowired
    private BookFeignClient restClient;

    @Test
    public void contextLoads() {

        System.out.println(restClient.getById(6L));

        System.out.println("List -------- ");

        System.out.println(restClient.getAll());
    }

}
