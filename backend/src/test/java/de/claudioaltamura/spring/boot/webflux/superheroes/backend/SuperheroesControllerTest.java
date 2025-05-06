package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SuperheroesControllerTest {

    WebTestClient webTestClient;

    SuperheroesService superheroesService;

    @BeforeEach
    void setUp() {
        superheroesService = mock(SuperheroesService.class);
        webTestClient = WebTestClient.bindToController(new SuperheroesController(superheroesService)).build();
    }

    @Test
    void shouldReturnSuperhero() {
        when(superheroesService.getSuperhero(2L))
                .thenReturn(Mono.just(new Superhero(2L, "Spiderman")));

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/superheroes/{id}").build(2))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Superhero.class)
                .value(superhero -> assertThat(superhero.getName()).isNotNull());
    }

    @Test
    void shouldReturnSuperheroes() {
        when(superheroesService.getSuperheroes())
                .thenReturn(Flux.just(new Superhero(1L, "Hulk"), new Superhero(2L, "Spiderman")));

        webTestClient
                .get()
                .uri("/superheroes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Superhero.class)
                .hasSize(2)
                .consumeWith(
                        response -> {
                            var superheroes = response.getResponseBody();
                            assertThat(superheroes).isNotNull();
                            System.out.println(superheroes);
                        });
    }
}
