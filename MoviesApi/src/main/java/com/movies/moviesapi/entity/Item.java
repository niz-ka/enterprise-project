package com.movies.moviesapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull(message = "Movie cannot be null")
    @JoinColumn(nullable = false)
    private Movie movie;

    @ManyToOne
    @NotNull(message = "Cart cannot be null")
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Cart cart;

    @Min(value = 0, message = "Quantity cannot be less than 0")
    @Column(nullable = false)
    private Integer quantity;

    public Item(Movie movie, Cart cart, Integer quantity) {
        this.movie = movie;
        this.cart = cart;
        this.quantity = quantity;
    }
}
