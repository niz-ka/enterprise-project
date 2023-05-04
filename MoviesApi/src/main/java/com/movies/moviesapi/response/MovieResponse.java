package com.movies.moviesapi.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class MovieResponse {
    private final String model = "MovieResponse";
    private Long id;
    private String title;
    private String categoryName;
    private Integer year;
    private String description;
    private BigDecimal price;
    private String image;
}
