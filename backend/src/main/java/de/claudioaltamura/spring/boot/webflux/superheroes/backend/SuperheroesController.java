package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SuperheroesController {

  @GetMapping("/superheroes/{id}")
  public Mono<Superhero> getSuperhero(@PathVariable("id") long id) {
    return Mono.just(new Superhero(id, randomAlphabetic(6)));
  }
}
