package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

  public void consume(long id) {
    webClient
        .get()
        .uri(SUPERHEROES_ID_URI, id)
        .retrieve()
        .bodyToMono(Superhero.class)
        .subscribe(s -> log.info("superhero: {}", s));
  }
}
