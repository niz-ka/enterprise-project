package com.movies.moviesapi.controller;

import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.response.MovieResponse;
import com.movies.moviesapi.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;
    private final ModelMapper modelMapper;

    public MovieController(MovieService movieService, ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    private MovieResponse convertToMovieResponse(Movie movie) {
        return modelMapper.map(movie, MovieResponse.class);
    }

    @GetMapping
    ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        List<MovieResponse> movieResponses = movies.stream()
                .map(this::convertToMovieResponse)
                .toList();
        return new ResponseEntity<>(movieResponses, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<List<MovieResponse>> searchMovies(@RequestParam(name = "categoryName") String categoryName) {
        List<Movie> searchedMovies = movieService.searchMovies(categoryName);
        List<MovieResponse> searchedMovieResponses = searchedMovies.stream()
                .map(this::convertToMovieResponse)
                .toList();
        return new ResponseEntity<>(searchedMovieResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MovieResponse> getMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovie(id);
        MovieResponse movieResponse = this.convertToMovieResponse(movie);
        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }
}
