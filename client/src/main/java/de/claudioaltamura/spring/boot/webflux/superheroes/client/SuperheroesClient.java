package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuperheroesClient {

  public static final String SUPERHEROES_ID_URI = "/superheroes/{id}";

  private final WebClient webClient;

  public Superhero getSuperheroBlocking(long id) {
    return webClient
        .get()
        .uri(SUPERHEROES_ID_URI, id)
        .retrieve()
        .bodyToMono(Superhero.class)
        .block();
  }

  public Mono<Superhero> getSuperhero(long id) {
    return webClient.get().uri(SUPERHEROES_ID_URI, id).retrieve().bodyToMono(Superhero.class);
  }

  public Flux<Superhero> getSuperheroes() {
    return webClient.get().uri("/superheroes").retrieve().bodyToFlux(Superhero.class);
  }
}
