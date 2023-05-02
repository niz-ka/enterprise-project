package com.movies.moviesapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Category cannot be null")
    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Min(value = 1800, message = "Year cannot be less than 1800")
    @Column(nullable = false)
    private Integer year;

    @NotEmpty(message = "Description cannot be empty")
    @Column(nullable = false)
    private String description;

    @Min(value = 0, message = "Price cannot be less than 0")
    @Column(nullable = false)
    private BigDecimal price;

    public Movie(String title, Category category, Integer year, String description, BigDecimal price) {
        this.title = title;
        this.category = category;
        this.year = year;
        this.description = description;
        this.price = price;
    }
}
