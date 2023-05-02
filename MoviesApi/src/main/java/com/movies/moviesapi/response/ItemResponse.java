package com.movies.moviesapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ItemResponse {
    private final String model = "ItemResponse";
    private Long movieId;

    @JsonProperty("title")
    private String movieTitle;

    @JsonProperty("price")
    private BigDecimal moviePrice;

    private Integer quantity;
}
