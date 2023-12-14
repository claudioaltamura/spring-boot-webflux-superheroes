package de.claudioaltamura.spring.boot.webflux.superheroes.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

public class SuperheroesClientTest {
  private static MockWebServer mockWebServer;
  private SuperheroesClient cut; // Class Under Test

  @BeforeAll
  static void setup() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void testGetSuperheroById() throws InterruptedException {
    this.cut =
        new SuperheroesClient(
            WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build());

    var mockResponse =
        new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody("{\"id\": 1, \"name\":\"A\"}");

    mockWebServer.enqueue(mockResponse);

    var result = cut.getSuperheroBlocking(1);

    assertEquals(1, result.getId());
    assertEquals("A", result.getName());

    RecordedRequest request = mockWebServer.takeRequest();
    assertEquals("/superheroes/1", request.getPath());
  }
}
