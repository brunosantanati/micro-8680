package me.fwfurtado.books.rest;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class BookRestClient {

    private RestTemplate rest;


    private String baseURL;

    public BookRestClient(RestTemplate rest, @Value("${books-api.url}") String baseURL) {
        this.rest = rest;
        this.baseURL = baseURL;
    }

    public Optional<BookView> getById(Long id) {
        try {
            BookView forObject = rest.getForObject(baseURL + "{id}", BookView.class, id);
            return Optional.ofNullable(forObject);
        }catch (RestClientException e) {
            return Optional.empty();
        }
    }

    public List<BookView> getAll() {
        return List.of(rest.getForObject(baseURL, BookView[].class));
//        ResponseEntity<List<BookView>> response = rest.exchange(baseURL, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
//        return response.getBody();
    }

    @Data
    static class BookView {
        private String title;
    }
}
