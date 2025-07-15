package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import de.claudioaltamura.spring.boot.webflux.superheroes.model.Superhero;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class SuperheroesService {

    private final ConcurrentMap<Long, Superhero> superheroes = new ConcurrentHashMap<>();

    private final Random random = new Random();

    public SuperheroesService() {
        this.superheroes.put(1L, new Superhero(1L, "Hulk"));
        this.superheroes.put(2L, new Superhero(2L, "Spiderman"));
    }

    public Mono<Superhero> getSuperhero(Long id) {
        return Mono.justOrEmpty(this.superheroes.get(id));
    }

    public Mono<Long> addSuperhero(Superhero superhero) {
        Long id = (random.nextLong() * 100) + 1;
        return Mono.fromSupplier(() -> this.superheroes.put(id, superhero)).thenReturn(id);
    }

    public Mono<Void> deleteSuperhero(Long id) {
        return Mono.fromSupplier(() -> this.superheroes.remove(id))
                .then();
    }

    public Flux<Superhero> getSuperheroes() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick ->
                        this.superheroes.values()
                                .stream()
                                .sorted(Comparator.comparing(Superhero::getName))
                                .toList()
                                .get(tick.intValue())
                )
                .take(this.superheroes.size());
    }

    public Mono<Superhero> getSuperheroByName(String name) {
        return Mono.justOrEmpty(
                this.superheroes.values()
                        .stream()
                        .filter(superhero -> superhero.getName().equalsIgnoreCase(name))
                        .findFirst()
            );
    }
}
