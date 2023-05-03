package com.movies.moviesapi.controller;

import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.CategoryRepository;
import com.movies.moviesapi.repository.MovieRepository;
import com.movies.moviesapi.response.MovieResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieControllerBroadIntegrationTest {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebTestClient webTestClient;

    private List<Movie> movies;

    @BeforeAll
    void seedDatabase() {
        List<Category> categories = categoryRepository.saveAll(
                Arrays.asList(
                        new Category("Drama"),
                        new Category("Horror")
                )
        );

        List<Movie> moviesList = Arrays.asList(
                new Movie("title1", categories.get(0), 1990, "desc1", new BigDecimal("10.10")),
                new Movie("title2", categories.get(0), 1991, "desc2", new BigDecimal("11.10")),
                new Movie("title3", categories.get(1), 1992, "desc3", new BigDecimal("12.10"))
        );

        movies = repository.saveAll(moviesList);
    }

    @AfterAll
    void cleanDatabase() {
        repository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getAllMovies() {
        webTestClient
                .get().uri("/api/movies")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(MovieResponse.class)
                .consumeWith(result -> {
                    List<MovieResponse> movieResponses = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(3, movieResponses.size());
                    assertEquals("title1", movieResponses.get(0).getTitle());
                    assertEquals("title2", movieResponses.get(1).getTitle());
                    assertEquals("title3", movieResponses.get(2).getTitle());
                });
    }

    @Test
    void getMovie() {
        Movie movie = movies.get(0);
        webTestClient
                .get().uri("/api/movies/{id}", movie.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(MovieResponse.class)
                .consumeWith(result -> {
                    MovieResponse movieResponse = Objects.requireNonNull(result.getResponseBody());
                    assertEquals("title1", movieResponse.getTitle());
                    assertEquals("Drama", movieResponse.getCategoryName());
                });
    }

    @Test
    void searchMovies() {
        String categoryParam = "Drama";
        webTestClient
                .get().uri(uriBuilder -> uriBuilder
                        .path("/api/movies/search")
                        .queryParam("categoryName", categoryParam)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(MovieResponse.class)
                .consumeWith(result -> {
                    List<MovieResponse> movieResponse = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(2, movieResponse.size());
                    assertEquals("title1", movieResponse.get(0).getTitle());
                    assertEquals("title2", movieResponse.get(1).getTitle());
                });
    }
}