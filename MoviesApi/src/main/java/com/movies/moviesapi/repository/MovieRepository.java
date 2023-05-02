package com.movies.moviesapi.repository;

import com.movies.moviesapi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByCategoryName(String name);
}
