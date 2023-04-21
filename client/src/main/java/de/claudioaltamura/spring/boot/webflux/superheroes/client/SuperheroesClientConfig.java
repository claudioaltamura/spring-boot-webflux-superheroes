package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class SuperheroesClientConfig {
    @Value("${superHeroesServerUrl}")
    private String superHeroesServerUrl;

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000) // millis
                .doOnConnected(connection ->
                        connection
                                .addHandlerLast(new ReadTimeoutHandler(2)) // seconds
                                .addHandlerLast(new WriteTimeoutHandler(2))); //seconds

        return builder
                .baseUrl(superHeroesServerUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "SuperheroesClientApp")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            log.info("--- Http Headers: ---");
            clientRequest.headers().entrySet().forEach(this::logHeader);
            return next.exchange(clientRequest);
        };
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response: {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().entrySet().forEach(this::logResponse);
            return Mono.just(clientResponse);
        });
    }

    private void logHeader(Map.Entry<String, List<String>> entry) {
        log.info("{}={}", entry.getKey(), entry.getValue());
    }

    private void logResponse(Map.Entry<String, List<String>> entry) {
        log.info("{}={}", entry.getKey(), entry.getValue());
    }

}
