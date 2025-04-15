package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@RestController
public class SuperheroesController {

  private final Random random = new Random();

  @GetMapping("/superheroes/{id}")
  public Mono<Superhero> getSuperhero(@PathVariable("id") long id) {
    return Mono.just(new Superhero(id, randomAlphabetic(6)));
  }

  @GetMapping("/superheroes")
  public Flux<Superhero> getSuperheroes() {
    return Flux.range(1, 10)
        .map(id ->
                new Superhero((random.nextLong() * 100) + 1, randomAlphabetic(6)))
        .delayElements(Duration.ofMillis(100));
  }

}
