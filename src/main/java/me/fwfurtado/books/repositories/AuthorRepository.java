package me.fwfurtado.books.repositories;

import java.util.Optional;
import me.fwfurtado.books.domain.Author;
import org.springframework.data.repository.Repository;

public interface AuthorRepository extends Repository<Author, Long> {

    Optional<Author> findById(Long id);

    void save(Author author);
}
