package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SuperheroesClientApp {


	@Autowired private SuperheroesClient superheroesClient;

	public static void main(String[] args) {
		SpringApplication.run(SuperheroesClientApp.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(SuperheroesClient superheroesClientApp) {
		return args ->
			log.info("superhero: {}", superheroesClient.getSuperheroBlocking(1));
	}

}
