package me.fwfurtado.books.rest;

import java.util.List;
import java.util.Optional;
import me.fwfurtado.books.rest.BookRestClient.BookView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${books-api.url}",name = "books", decode404 = true)
public interface BookFeignClient {

    @GetMapping("{id}")
    Optional<BookView> getById(@PathVariable Long id);

    @GetMapping
    List<BookView> getAll();

}
