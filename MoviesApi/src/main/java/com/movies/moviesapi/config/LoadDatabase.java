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
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("!test")
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
                    "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
                    new BigDecimal("14.99"),
                    "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_FMjpg_UX1200_.jpg"
            );

            Movie midnightMovie = new Movie(
                    "Midnight in Paris",
                    comedyCategory,
                    2011,
                    "While on a trip to Paris with his fiancée's family, a nostalgic screenwriter finds himself mysteriously going back to the 1920s every day at midnight.",
                    new BigDecimal("12.99"),
                    "https://m.media-amazon.com/images/M/MV5BMTM4NjY1MDQwMl5BMl5BanBnXkFtZTcwNTI3Njg3NA@@._V1_FMjpg_UX890_.jpg"
            );

            Movie shiningMovie = new Movie(
                    "The Shining",
                    horrorCategory,
                    1980,
                    "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence, while his psychic son sees horrific forebodings from both past and future.",
                    new BigDecimal("14.99"),
                    "https://m.media-amazon.com/images/M/MV5BZWFlYmY2MGEtZjVkYS00YzU4LTg0YjQtYzY1ZGE3NTA5NGQxXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_FMjpg_UX627_.jpg"
            );

            Movie inceptionMovie = new Movie(
                    "Inception",
                    dramaCategory,
                    2010,
                    "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
                    new BigDecimal("10.00"),
                    "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_FMjpg_UX700_.jpg"
            );

            Movie hangoverMovie = new Movie(
                    "The Hangover",
                    comedyCategory,
                    2009,
                    "Three buddies wake up from a bachelor party in Las Vegas, with no memory of the previous night and the bachelor missing. They make their way around the city in order to find their friend before his wedding.",
                    new BigDecimal("13.99"),
                    "https://m.media-amazon.com/images/M/MV5BNGQwZjg5YmYtY2VkNC00NzliLTljYTctNzI5NmU3MjE2ODQzXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_FMjpg_UY4592_.jpg"
            );

            Movie nosferatuMovie = new Movie(
                    "Nosferatu, eine Symphonie des Grauens",
                    horrorCategory,
                    1922,
                    "Vampire Count Orlok expresses interest in a new residence and real estate agent Hutter's wife.",
                    new BigDecimal("25.99"),
                    "https://m.media-amazon.com/images/M/MV5BNTRkZGEwMTMtZjhmMC00YWNhLWFjZjAtNzBiZjU0ODRjMjE0XkEyXkFqcGdeQXVyNjc5NjEzNA@@._V1_FMjpg_UX1000_.jpg"
            );

            Movie whiplashMovie = new Movie(
                    "Whiplash",
                    dramaCategory,
                    2014,
                    "A promising young drummer enrolls at a cut-throat music conservatory where his dreams of greatness are mentored by an instructor who will stop at nothing to realize a student's potential.",
                    new BigDecimal("16.99"),
                    "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_FMjpg_UY5333_.jpg"
            );

            Movie psychoMovie = new Movie(
                    "Psycho",
                    horrorCategory,
                    1960,
                    "A Phoenix secretary embezzles $40,000 from her employer's client, goes on the run and checks into a remote motel run by a young man under the domination of his mother.",
                    new BigDecimal("18.99"),
                    "https://m.media-amazon.com/images/M/MV5BNTQwNDM1YzItNDAxZC00NWY2LTk0M2UtNDIwNWI5OGUyNWUxXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_FMjpg_UY2676_.jpg"
            );

            Movie parasiteMovie = new Movie(
                    "Parasite",
                    dramaCategory,
                    2019,
                    "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                    new BigDecimal("26.99"),
                    "https://m.media-amazon.com/images/M/MV5BYWZjMjk3ZTItODQ2ZC00NTY5LWE0ZDYtZTI3MjcwN2Q5NTVkXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_FMjpg_UY3556_.jpg"
            );


            List<Category> categories = Arrays.asList(dramaCategory, comedyCategory, horrorCategory);

            List<Movie> movies = Arrays.asList(
                    shawshankMovie,
                    midnightMovie,
                    shiningMovie,
                    inceptionMovie,
                    hangoverMovie,
                    nosferatuMovie,
                    whiplashMovie,
                    psychoMovie,
                    parasiteMovie);

            categoryRepository.saveAll(categories);
            log.info("Categories loaded");

            movieRepository.saveAll(movies);
            log.info("Movies loaded");
        };
    }
}
