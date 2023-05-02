package com.movies.moviesapi.config;

import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.model.Movie;
import com.movies.moviesapi.repository.CategoryRepository;
import com.movies.moviesapi.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        return args -> {
            Category dramaCategory = new Category("Drama");
            Category comedyCategory = new Category("Comedy");
            Category horrorCategory = new Category("Horror");

            Movie shawshankMovie = new Movie(
                    "The Shawshank Redemption",
                    dramaCategory,
                    1994,
                    "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion",
                    new BigDecimal("14.99")
            );

            Movie midnightMovie = new Movie(
                    "Midnight in Paris",
                    comedyCategory,
                    2011,
                    "While on a trip to Paris with his fiancée's family, a nostalgic screenwriter finds himself mysteriously going back to the 1920s every day at midnight.",
                    new BigDecimal("12.99")
            );

            Movie shiningMovie = new Movie(
                    "The Shining",
                    horrorCategory,
                    1980,
                    "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence, while his psychic son sees horrific forebodings from both past and future.",
                    new BigDecimal("14.99")
            );

            log.info("Preloading " + categoryRepository.save(dramaCategory));
            log.info("Preloading " + categoryRepository.save(comedyCategory));
            log.info("Preloading " + categoryRepository.save(horrorCategory));

            log.info("Preloading " + movieRepository.save(shawshankMovie));
            log.info("Preloading " + movieRepository.save(midnightMovie));
            log.info("Preloading " + movieRepository.save(shiningMovie));
        };
    }
}
