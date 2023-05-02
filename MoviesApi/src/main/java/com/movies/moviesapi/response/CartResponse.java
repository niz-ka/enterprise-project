package com.movies.moviesapi.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CartResponse {
    private final String model = "CartResponse";
    private UUID id;
    private BigDecimal total;
    private List<ItemResponse> items;
}
