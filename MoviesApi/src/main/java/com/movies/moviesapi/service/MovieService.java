package com.movies.moviesapi.service;

import com.movies.moviesapi.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> searchMovies(String categoryName);

    Movie getMovie(Long id);
}
