package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

public class SuperheroesGlobalErrrorHandler {

  @ExceptionHandler(ApplicationException.class)
  public Mono<ResponseEntity<ApplicationError>> handleCustomRuntimeException(
      ApplicationException cre) {
    return Mono.just(
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApplicationError(500, cre.getMessage())));
  }
}
