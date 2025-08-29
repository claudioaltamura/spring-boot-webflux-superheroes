package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class SuperheroesServiceTest {

    private final SuperheroesService superheroesService = new SuperheroesService();

    @Test
    void getSuperhero() {
        StepVerifier.create(superheroesService.getSuperhero(1L))
                .expectNextMatches(
                        superhero -> superhero.getId().equals(1L) &&
                                superhero.getName().equals("Hulk"))
                .verifyComplete();
    }

    @Test
    void getSuperheroNotFound() {
        StepVerifier.create(superheroesService.getSuperhero(99L))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getSuperheroByName() {
        StepVerifier.create(superheroesService.getSuperheroByName("Spiderman"))
                .expectNextMatches(
                        superhero -> superhero.getId().equals(2L) &&
                                superhero.getName().equals("Spiderman"))
                .verifyComplete();
    }

    @Test
    void getSuperheroByNameNotFound() {
        StepVerifier.create(superheroesService.getSuperheroByName("Flash"))
                .verifyComplete();
    }

    @Test
    void addSuperhero() {
        StepVerifier.create(superheroesService.addSuperhero(new Superhero(null, "Batman")))
                .expectNextMatches(superhero -> superhero != null && superhero.getId() > 0)
                .verifyComplete();
    }

    @Test
    void deleteSuperhero() {
        StepVerifier.create(superheroesService.deleteSuperhero(1L))
                .verifyComplete();

        StepVerifier.create(superheroesService.getSuperheroes())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getSuperheroes() {
        StepVerifier.create(superheroesService.getSuperheroes())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void generateError() {
        StepVerifier.create(superheroesService.generateError())
                .expectError(ApplicationException.class)
                .verify();
    }
}