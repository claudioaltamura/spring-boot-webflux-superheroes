package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

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

  @GetMapping("/superheroes/search")
  public Mono<Superhero> getSuperheroByName(@RequestParam("name") String name) {
    return superheroesService.getSuperheroByName(name);
  }

  @GetMapping("/superheroes")
  public Flux<Superhero> getSuperheroes() {
    return superheroesService.getSuperheroes();
  }

  @PostMapping("/superheroes")
  public Mono<ResponseEntity<Long>> add(Superhero superhero) {
    return superheroesService.addSuperhero((superhero))
            .map(id -> ResponseEntity.created(URI.create("/superheroes/" + id)).build());
  }

}
