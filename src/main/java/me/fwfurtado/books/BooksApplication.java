package me.fwfurtado.books;

import me.fwfurtado.books.domain.Author;
import me.fwfurtado.books.repositories.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@SpringBootApplication
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    CommandLineRunner commandLineRunner(AuthorRepository repository) {
        return args -> {

            repository.save(new Author("Fernando"));
            repository.save(new Author("Thiago"));
            repository.save(new Author("Yuri"));
            repository.save(new Author("Lucas"));

        };
    }
}
