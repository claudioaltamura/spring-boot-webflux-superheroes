package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SuperheroesController {

  private final SuperheroesService superheroesService;

  public SuperheroesController(SuperheroesService superheroesService) {
      this.superheroesService = superheroesService;
  }

  @GetMapping("/superheroes/{id}")
  public Mono<Superhero> getSuperhero(@PathVariable("id") long id) {
    return superheroesService.getSuperhero(id);
  }

  @GetMapping("/superheroes")
  public Flux<Superhero> getSuperheroes() {
    return superheroesService.getSuperheroes();
  }

}
