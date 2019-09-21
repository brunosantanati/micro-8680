package me.fwfurtado.books.repositories;

import java.util.List;
import java.util.Optional;
import me.fwfurtado.books.domain.Book;
import org.springframework.data.repository.Repository;

public interface BookRepository extends Repository<Book, Long> {
    Optional<Book> findById(Long id);

    List<Book> findAll();

    void save(Book book);
}
