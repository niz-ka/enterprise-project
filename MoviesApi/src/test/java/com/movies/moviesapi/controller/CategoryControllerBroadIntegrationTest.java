package com.movies.moviesapi.controller;

import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.repository.CategoryRepository;
import com.movies.moviesapi.response.CategoryResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerBroadIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CategoryRepository repository;

    @BeforeAll
    void seedDatabase() {
        List<Category> categories = Arrays.asList(
                new Category("Drama"),
                new Category("Horror")
        );
        repository.saveAll(categories);
    }

    @AfterAll
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void getAllCategories() {
        webTestClient
                .get().uri("/api/categories")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(CategoryResponse.class)
                .consumeWith(result -> {
                    List<CategoryResponse> categoryResponses = Objects.requireNonNull(result.getResponseBody());
                    assertEquals(2, categoryResponses.size());
                    assertEquals("Drama", categoryResponses.get(0).getName());
                    assertEquals("Horror", categoryResponses.get(1).getName());
                });
    }
}