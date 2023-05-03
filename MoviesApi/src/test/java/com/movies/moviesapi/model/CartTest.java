package com.movies.moviesapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    private Cart cart;

    @BeforeEach
    void initCart() {
        this.cart = new Cart();
    }

    @Test
    void Should_ReturnZero_When_ItemsListIsNull() {
        cart.setItems(null);

        assertEquals(BigDecimal.ZERO, cart.getTotal());
    }

    @Test
    void Should_ReturnZero_When_ItemsListIsEmpty() {
        cart.setItems(new ArrayList<>());

        assertEquals(BigDecimal.ZERO, cart.getTotal());
    }

    @Test
    void Should_ReturnCorrectTotal_When_SomeItemsInList() {
        List<Movie> movies = Arrays.asList(
                new Movie(null, null, null, null, new BigDecimal("15.25")),
                new Movie(null, null, null, null, new BigDecimal("21.30"))
        );
        List<Item> items = Arrays.asList(
                new Item(movies.get(0), null, 3),
                new Item(movies.get(1), null, 1)
        );

        cart.setItems(items);

        assertEquals(new BigDecimal("67.05"), cart.getTotal());
    }
}