package com.movies.moviesapi.service;

import com.movies.moviesapi.model.Cart;
import com.movies.moviesapi.request.ItemRequest;

import java.util.UUID;

public interface CartService {
    Cart getCart(UUID id);

    Cart replaceItemInCart(ItemRequest itemRequest, UUID id);

    Cart createCart();

    void deleteCart(UUID id);
}
