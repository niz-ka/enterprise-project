package com.movies.moviesapi.repository;

import com.movies.moviesapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findFirstByCartIdAndMovieId(UUID cartId, Long movieId);
}
