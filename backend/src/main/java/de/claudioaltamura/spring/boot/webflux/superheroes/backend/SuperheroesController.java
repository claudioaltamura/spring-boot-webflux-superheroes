package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SuperheroesController {

  private final SuperheroesService superheroesService;

  public SuperheroesController(SuperheroesService superheroesService) {
      this.superheroesService = superheroesService;
  }

  @GetMapping("/superheroes/{id}")
  public Mono<ResponseEntity<Superhero>> getSuperhero(@PathVariable("id") long id) {
    return superheroesService.getSuperhero(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
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
  public Mono<ResponseEntity<Long>> add(@Valid @RequestBody Superhero superhero, UriComponentsBuilder uriBuilder) {
    return superheroesService.addSuperhero(superhero)
            .map(id -> ResponseEntity.created(
                    uriBuilder.path("/superheroes/{id}").buildAndExpand(id).toUri()
            ).build());
  }

  @DeleteMapping("/superheroes/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable("id") long id) {
    return superheroesService.deleteSuperhero(id)
            .thenReturn(ResponseEntity.noContent().build());
  }

}
