package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.model.Cart;
import com.movies.moviesapi.model.Item;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.CartRepository;
import com.movies.moviesapi.repository.ItemRepository;
import com.movies.moviesapi.repository.MovieRepository;
import com.movies.moviesapi.request.ItemRequest;
import com.movies.moviesapi.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final ItemRepository itemRepository;
    private final MovieRepository movieRepository;

    public CartServiceImpl(
            CartRepository repository,
            ItemRepository itemRepository,
            MovieRepository movieRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Cart getCart(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart " + id + " not found"));
    }

    @Override
    public Cart replaceItemInCart(ItemRequest itemRequest, UUID id) {
        Optional<Item> oldItem = itemRepository.findFirstByCartIdAndMovieId(id, itemRequest.getMovieId());

        if (oldItem.isPresent()) {
            Item oldPresentItem = oldItem.get();
            if (itemRequest.getQuantity() > 0) {
                oldPresentItem.setQuantity(itemRequest.getQuantity());
                itemRepository.save(oldPresentItem);
            } else {
                itemRepository.deleteById(oldPresentItem.getId());
            }
        } else if (itemRequest.getQuantity() > 0) {
            Movie movie = movieRepository
                    .findById(itemRequest.getMovieId())
                    .orElseThrow(() -> new EntityNotFoundException("Movie " + itemRequest.getMovieId() + " not found"));
            Cart cart = getCart(id);
            Item newItem = new Item(movie, cart, itemRequest.getQuantity());
            itemRepository.save(newItem);
        }

        return getCart(id);
    }

    @Override
    public Cart createCart() {
        return repository.save(new Cart());
    }

    @Override
    public void deleteCart(UUID id) {
        repository.deleteById(id);
    }
}
