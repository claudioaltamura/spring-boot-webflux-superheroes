package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperheroesControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnSuperhero() {
        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/superheroes/{id}")
                        .build(2))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Superhero.class).value(superhero -> assertThat(superhero.getName()).isNotNull());
    }
}