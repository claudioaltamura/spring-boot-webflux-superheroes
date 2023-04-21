package de.claudioaltamura.spring.boot.webflux.superheroes.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperheroTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testSerialize() throws JsonProcessingException {
        var superhero = new Superhero(1L, "Superhero");

        var result = objectMapper.writeValueAsString(superhero);

        assertEquals("{\"id\":1,\"name\":\"Superhero\"}", result);
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        var superheroAsJson = "{\"id\":1,\"name\":\"Superhero\"}";

        var superhero = objectMapper.readValue(superheroAsJson, Superhero.class);

        assertEquals(new Superhero(1L, "Superhero"), superhero);
    }
}