package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    MovieRepository repository;

    @InjectMocks
    MovieServiceImpl movieService;


    private final static String cat1 = "cat1";
    private final static List<Movie> movies = Arrays.asList(
            new Movie("title1", new Category("cat1"), 1990, "desc1", new BigDecimal("10.20")),
            new Movie("title2", new Category("cat1"), 1995, "desc2", new BigDecimal("20.20")),
            new Movie("title3", new Category("cat3"), 1997, "desc3", new BigDecimal("30.03"))
    );

    @Test
    void Should_ReturnMovieList_When_Called() {
        Mockito.when(repository.findAll())
                .thenReturn(movies);

        assertEquals(movies, movieService.getAllMovies());
    }

    @Test
    void Should_ReturnMoviesBelongingToCategory_When_SearchedByCategoryName() {
        List<Movie> moviesCat1 = movies.stream()
                .filter(movie -> Objects.equals(movie.getCategory().getName(), cat1))
                .toList();
        Mockito.when(repository.findByCategoryName(cat1))
                .thenReturn(moviesCat1);

        assertEquals(moviesCat1, movieService.searchMovies(cat1));
    }

    @Test
    void Should_ReturnMovie_When_MovieWithGivenIdExists() {
        Movie movie = new Movie();
        movie.setId(100L);
        Mockito.when(repository.findById(100L))
                .thenReturn(Optional.of(movie));

        assertEquals(movie, movieService.getMovie(100L));
    }

    @Test
    void Should_ThrowEntityNotFoundException_When_MovieWithGivenIdNotExists() {
        Mockito.when(repository.findById(100L))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> movieService.getMovie(100L));
        assertEquals("Movie 100 not found", exception.getMessage());
    }
}