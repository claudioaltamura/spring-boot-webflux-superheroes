package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class SuperheroesControllerTest {

    WebTestClient webTestClient;

    SuperheroesService superheroesService;

    @BeforeEach
    void setUp() {
        superheroesService = mock(SuperheroesService.class);
        webTestClient = WebTestClient.bindToController(new SuperheroesController(superheroesService)).build();
    }

    @Test
    void getSuperhero() {
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
                .value(superhero -> assertThat(superhero.getId()).isEqualTo(2L));

        verify(superheroesService, times(1)).getSuperhero(2L);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroNotFound() {
        when(superheroesService.getSuperhero(2L))
                .thenReturn(Mono.empty());

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/superheroes/{id}").build(2))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        verify(superheroesService, times(1)).getSuperhero(2L);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroByName() {
        when(superheroesService.getSuperheroByName("Spiderman"))
                .thenReturn(Mono.just(new Superhero(2L, "Spiderman")));

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/superheroes/search")
                        .queryParam("name", "Spiderman")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Superhero.class)
                .value(superhero -> assertThat(superhero.getName()).isEqualTo("Spiderman"));

        verify(superheroesService, times(1)).getSuperheroByName("Spiderman");
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getEmptySuperheroList() {
        when(superheroesService.getSuperheroByName("Spiderman"))
                .thenReturn(Mono.empty());

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/superheroes/search")
                        .queryParam("name", "Spiderman")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Superhero.class)
                .hasSize(0);

        verify(superheroesService, times(1)).getSuperheroByName("Spiderman");
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void getSuperheroes() {
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
                        });

        verify(superheroesService, times(1)).getSuperheroes();
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void addSuperhero() {
        Superhero newSuperhero = new Superhero(null, "Ironman");
        when(superheroesService.addSuperhero(any(Superhero.class)))
                .thenReturn(Mono.just(3L));

        webTestClient
                .post()
                .uri("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newSuperhero)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectHeader().location("/superheroes/3");

        verify(superheroesService, times(1)).addSuperhero(any(Superhero.class));
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void addInvalidASuperhero() {
        webTestClient
                .post()
                .uri("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"description\":\"Description\"}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isBadRequest();

        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void deleteSuperhero() {
        when(superheroesService.deleteSuperhero(2L))
                .thenReturn(Mono.empty());

        webTestClient
                .delete()
                .uri(uriBuilder -> uriBuilder.path("/superheroes/{id}").build(2))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNoContent();

        verify(superheroesService, times(1)).deleteSuperhero(2L);
        verifyNoMoreInteractions(superheroesService);
    }

    @Test
    void generateError() {
        when(superheroesService.generateError())
                .thenReturn(Mono.error(new ApplicationException("some kind of error from somewhere")));

        webTestClient
                .get()
                .uri("/error")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is5xxServerError();


        verify(superheroesService).generateError();
        verifyNoMoreInteractions(superheroesService);
    }
}
