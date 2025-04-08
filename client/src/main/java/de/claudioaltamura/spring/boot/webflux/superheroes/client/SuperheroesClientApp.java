package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SuperheroesClientApp {

  private final SuperheroesClient superheroesClient;

  public SuperheroesClientApp(SuperheroesClient superheroesClient) {
    this.superheroesClient = superheroesClient;
  }

  public static void main(String[] args) {
    SpringApplication.run(SuperheroesClientApp.class, args);
  }

  @Bean
  public ApplicationRunner applicationRunner(SuperheroesClient superheroesClientApp) {
    return args -> superheroesClient.consume(1).subscribe(s -> log.info("superhero: {}", s));
  }
}
