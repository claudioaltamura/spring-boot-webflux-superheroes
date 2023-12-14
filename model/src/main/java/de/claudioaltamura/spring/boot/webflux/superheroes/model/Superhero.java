package de.claudioaltamura.spring.boot.webflux.superheroes.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Superhero {
  private Long id;
  private String name;
}
