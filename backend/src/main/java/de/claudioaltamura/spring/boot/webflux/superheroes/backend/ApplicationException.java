package de.claudioaltamura.spring.boot.webflux.superheroes.backend;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}