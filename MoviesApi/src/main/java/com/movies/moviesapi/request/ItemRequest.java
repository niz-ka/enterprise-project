package com.movies.moviesapi.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private Long movieId;

    @Min(value = 0, message = "Quantity cannot be less than 0")
    private Integer quantity;
}
