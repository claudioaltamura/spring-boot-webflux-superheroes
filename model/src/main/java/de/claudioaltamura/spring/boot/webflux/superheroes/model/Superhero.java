package de.claudioaltamura.spring.boot.webflux.superheroes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Superhero {
    private  Long id;
    private String name;
}
