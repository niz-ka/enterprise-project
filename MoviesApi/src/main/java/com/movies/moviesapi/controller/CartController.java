package com.movies.moviesapi.controller;

import com.movies.moviesapi.entity.Cart;
import com.movies.moviesapi.request.ItemRequest;
import com.movies.moviesapi.response.CartResponse;
import com.movies.moviesapi.service.CartService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;
    private final ModelMapper modelMapper;

    public CartController(CartService cartService, ModelMapper modelMapper) {
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    private CartResponse convertToCartResponse(Cart cart) {
        return modelMapper.map(cart, CartResponse.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable UUID id) {
        Cart cart = cartService.getCart(id);
        CartResponse cartResponse = this.convertToCartResponse(cart);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartResponse> replaceItemInCart(
            @RequestBody @Valid ItemRequest itemRequest,
            @PathVariable UUID id) {
        Cart cart = cartService.replaceItemInCart(itemRequest, id);
        CartResponse cartResponse = this.convertToCartResponse(cart);
        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartResponse> newCart() {
        Cart cart = cartService.createCart();
        CartResponse cartResponse = this.convertToCartResponse(cart);
        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable UUID id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
