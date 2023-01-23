package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuperheroesClient {

    private final WebClient webClient;

    public void consume(long id) {
        Mono<Superhero> superhero = webClient.get()
                .uri("/superheroes/{id}", id)
                .retrieve()
                .bodyToMono(Superhero.class);

        superhero.subscribe(s -> log.info("superhero: {}", s));
    }

}
