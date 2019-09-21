package me.fwfurtado.books.controllers;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import me.fwfurtado.books.domain.Author;
import me.fwfurtado.books.domain.Book;
import me.fwfurtado.books.repositories.AuthorRepository;
import me.fwfurtado.books.repositories.BookRepository;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @GetMapping
    List<Book> list() {
        return bookRepository.findAll();
    }

    static class BookHateoas extends ResourceSupport {

        @Getter  private final String title;
        @Getter private final List<String> authors;

        BookHateoas(Book book) {
            Long id = book.getId();
            this.title = book.getTitle();
            this.authors = book.getAuthors().stream().map(Author::getName).collect(toList());

            add(linkTo(methodOn(BookController.class).show(id)).withSelfRel());
            add(linkTo(methodOn(BookController.class).showAuthors(id)).withRel("authors"));
        }
    }

    @GetMapping("{id}")
    ResponseEntity<BookHateoas> show(@PathVariable Long id) {
        return bookRepository.findById(id).map(BookHateoas::new).map(ok()::body).orElseGet(notFound()::build);
    }

    @GetMapping("{id}/authors")
    ResponseEntity<List<Author>> showAuthors(@PathVariable Long id) {
        return bookRepository.findById(id).map(Book::getAuthors).map(ok()::body).orElseGet(notFound()::build);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody BookForm bookForm, UriComponentsBuilder uriBuilder) {

        List<Author> authors = bookForm.authors().map(authorRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());

        Book book = new Book(bookForm.title, authors);

        bookRepository.save(book);

        URI uri = uriBuilder.path("{id}").build(book.getId());

        return created(uri).build();
    }

    @Data
    static class BookForm {
        private String title;
        private Long[] authorsId;

        Stream<Long> authors() {
            return Stream.of(authorsId);
        }
    }
}
