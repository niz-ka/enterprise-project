package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.model.Cart;
import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.model.Item;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.CartRepository;
import com.movies.moviesapi.repository.ItemRepository;
import com.movies.moviesapi.repository.MovieRepository;
import com.movies.moviesapi.request.ItemRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock
    CartRepository repository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    MovieRepository movieRepository;
    @InjectMocks
    CartServiceImpl cartService;

    private Cart cart;
    private Movie movie;
    private ItemRequest itemRequest;

    @BeforeEach
    void initTest() {
        cart = new Cart(UUID.randomUUID(), new ArrayList<>());
        movie = new Movie(100L, ":title:", new Category(":cat:"), 1990, ":desc:", new BigDecimal("20.20"));
        itemRequest = new ItemRequest(100L, 1);
    }

    @Test
    void Should_ReturnCart_When_CartWithGivenIdExists() {
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.of(cart));

        assertEquals(cart, cartService.getCart(cart.getId()));
    }

    @Test
    void Should_ThrowEntityNotFoundException_When_CartWithGivenIdNotExists() {
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> cartService.getCart(cart.getId()));
        assertEquals("Cart " + cart.getId() + " not found", exception.getMessage());
    }

    @Test
    void Should_CreateCart_When_Called() {
        Mockito.when(repository.save(new Cart()))
                .thenReturn(new Cart());

        assertEquals(new Cart(), cartService.createCart());
    }

    @Test
    void Should_DeleteCart_When_CartIdGiven() {
        cartService.deleteCart(cart.getId());

        verify(repository, times(1))
                .deleteById(cart.getId());
    }

    @Test
    void Should_AddNewItemToCart_When_NewItemGiven() {
        Item expectedItem = new Item(null, movie, cart, itemRequest.getQuantity());
        Cart expectedCart = new Cart(cart.getId(), List.of(expectedItem));
        Mockito.when(itemRepository.findFirstByCartIdAndMovieId(cart.getId(), movie.getId()))
                .thenReturn(Optional.empty());
        Mockito.when(movieRepository.findById(movie.getId()))
                .thenReturn(Optional.of(movie));
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.of(cart))
                .thenReturn(Optional.of(expectedCart));
        Mockito.when(itemRepository.save(expectedItem))
                .thenReturn(expectedItem);

        Cart actualCart = cartService.replaceItemInCart(itemRequest, cart.getId());

        assertEquals(expectedCart, actualCart);
        verify(itemRepository).findFirstByCartIdAndMovieId(cart.getId(), movie.getId());
        verify(movieRepository).findById(movie.getId());
        verify(repository, times(2)).findById(cart.getId());
        verify(itemRepository).save(expectedItem);
    }

    @Test
    void Should_ChangeItemQuantity_When_GivenItemAlreadyInCartAndGivenQuantityGreaterThanZero() {
        Item oldItem = new Item(movie, cart, 3);
        cart.setItems(List.of(oldItem));
        Cart expectedCart = new Cart(cart.getId(), new ArrayList<>());
        Item expectedItem = new Item(movie, cart, itemRequest.getQuantity());
        expectedCart.setItems(List.of(expectedItem));
        Mockito.when(itemRepository.findFirstByCartIdAndMovieId(cart.getId(), movie.getId()))
                .thenReturn(Optional.of(oldItem));
        Mockito.when(itemRepository.save(new Item(movie, cart, 1)))
                .thenReturn(new Item(movie, cart, 1));
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.of(expectedCart));

        Cart actualCart = cartService.replaceItemInCart(itemRequest, cart.getId());

        assertEquals(expectedCart, actualCart);
        verify(itemRepository).findFirstByCartIdAndMovieId(cart.getId(), movie.getId());
        verify(itemRepository).save(new Item(movie, cart, 1));
        verify(repository).findById(cart.getId());
    }

    @Test
    void Should_DeleteItem_When_GivenItemAlreadyInCartAndGivenQuantityIsEqualZero() {
        itemRequest.setQuantity(0);
        Item oldItem = new Item(movie, cart, 3);
        cart.setItems(List.of(oldItem));
        Cart expectedCart = new Cart(cart.getId(), new ArrayList<>());
        Mockito.when(itemRepository.findFirstByCartIdAndMovieId(cart.getId(), movie.getId()))
                .thenReturn(Optional.of(oldItem));
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.of(expectedCart));

        Cart actualCart = cartService.replaceItemInCart(itemRequest, cart.getId());

        assertEquals(expectedCart, actualCart);
        verify(itemRepository).findFirstByCartIdAndMovieId(cart.getId(), movie.getId());
        verify(repository).findById(cart.getId());
    }

    @Test
    void Should_IgnoreRequest_When_GivenNewItemQuantityIsZero() {
        itemRequest.setQuantity(0);
        Mockito.when(itemRepository.findFirstByCartIdAndMovieId(cart.getId(), 100L))
                .thenReturn(Optional.empty());
        Mockito.when(repository.findById(cart.getId()))
                .thenReturn(Optional.of(cart));

        Cart actualCart = cartService.replaceItemInCart(itemRequest, cart.getId());

        assertEquals(cart, actualCart);
        verify(itemRepository).findFirstByCartIdAndMovieId(cart.getId(), 100L);
        verify(repository).findById(cart.getId());
    }
}