package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SuperheroesClientAppConfig {
    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(baseUrl).build();
    }
}
