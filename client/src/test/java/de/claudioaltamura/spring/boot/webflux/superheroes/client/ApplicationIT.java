package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIT {

	@Autowired
	private WebTestClient webTestClient;
	@Test
	void contextLoads() {
	}

}
