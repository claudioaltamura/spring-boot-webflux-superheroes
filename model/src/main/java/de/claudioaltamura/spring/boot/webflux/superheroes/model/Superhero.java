package de.claudioaltamura.spring.boot.webflux.superheroes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Superhero {
    private  Long id;
    private String name;
}
