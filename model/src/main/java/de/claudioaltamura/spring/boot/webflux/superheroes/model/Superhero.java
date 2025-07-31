package de.claudioaltamura.spring.boot.webflux.superheroes.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Superhero {
  private Long id;
  @NotEmpty(message = "Name cannot be empty")
  private String name;
}
