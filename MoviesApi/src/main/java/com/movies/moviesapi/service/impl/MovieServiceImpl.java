package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.entity.Movie;
import com.movies.moviesapi.repository.MovieRepository;
import com.movies.moviesapi.service.MovieService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    @Override
    public List<Movie> searchMovies(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    @Override
    public Movie getMovie(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie " + id + " not found"));
    }
}
