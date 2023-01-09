package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SuperheroesClientApp {


	@Autowired private SuperheroesClient superheroesClientApp;
	public static void main(String[] args) {
		SpringApplication.run(SuperheroesClientApp.class, args);
	}

	@Bean
	public CommandLineRunner runner(SuperheroesClient superheroesClientApp) {
		return args -> { superheroesClientApp.consume(1);};
	}
}
