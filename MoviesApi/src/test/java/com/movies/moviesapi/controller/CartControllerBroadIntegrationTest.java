package com.movies.moviesapi.controller;

import com.movies.moviesapi.model.Cart;
import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.CartRepository;
import com.movies.moviesapi.repository.CategoryRepository;
import com.movies.moviesapi.repository.ItemRepository;
import com.movies.moviesapi.repository.MovieRepository;
import com.movies.moviesapi.request.ItemRequest;
import com.movies.moviesapi.response.CartResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartControllerBroadIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CartRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    void seedDatabase() {
        List<Category> categories = categoryRepository.saveAll(
                Arrays.asList(
                        new Category(1L, "Drama", new ArrayList<>()),
                        new Category(2L, "Horror", new ArrayList<>()
                        )
                ));

        List<Movie> movies = Arrays.asList(
                new Movie(1L, "title1", categories.get(0), 1990, "desc1", new BigDecimal("10.10")),
                new Movie(2L, "title2", categories.get(0), 1991, "desc2", new BigDecimal("11.10")),
                new Movie(3L, "title3", categories.get(1), 1992, "desc3", new BigDecimal("12.10"))
        );
        movieRepository.saveAll(movies);
    }

    @AfterAll
    void cleanDatabase() {
        itemRepository.deleteAll();
        repository.deleteAll();
        movieRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void createCart() {
        webTestClient
                .post().uri("/api/carts")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CartResponse.class)
                .consumeWith(result -> {
                    CartResponse cartResponse = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(BigDecimal.ZERO, cartResponse.getTotal());
                    assertTrue(cartResponse.getItems().isEmpty());
                    assertTrue(repository.findById(cartResponse.getId()).isPresent());
                });
    }

    @Test
    void replaceItemInCart() {
        Cart cart = repository.save(new Cart());
        ItemRequest itemRequest = new ItemRequest(1L, 2);
        webTestClient
                .patch().uri("/api/carts/{id}", cart.getId())
                .bodyValue(itemRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CartResponse.class)
                .consumeWith(result -> {
                    CartResponse cartResponse = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(new BigDecimal("20.20"), cartResponse.getTotal());
                    assertEquals(1, cartResponse.getItems().size());
                    assertEquals(1L, cartResponse.getItems().get(0).getMovieId());
                    assertTrue(itemRepository.findFirstByCartIdAndMovieId(cart.getId(), 1L).isPresent());
                });
    }

    @Test
    void getCart() {
        Cart cart = repository.save(new Cart());
        webTestClient
                .get().uri("/api/carts/{id}", cart.getId())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CartResponse.class)
                .consumeWith(result -> {
                    CartResponse cartResponse = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(BigDecimal.ZERO, cartResponse.getTotal());
                    assertTrue(cartResponse.getItems().isEmpty());
                });
    }

    @Test
    void deleteCart() {
        Cart cart = repository.save(new Cart());
        webTestClient
                .delete().uri("/api/carts/{id}", cart.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertTrue(repository.findById(cart.getId()).isEmpty());
    }
}