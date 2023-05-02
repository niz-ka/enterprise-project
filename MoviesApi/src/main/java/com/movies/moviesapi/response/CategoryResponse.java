package com.movies.moviesapi.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponse {
    private final String model = "CategoryResponse";
    private Long id;
    private String name;
}
