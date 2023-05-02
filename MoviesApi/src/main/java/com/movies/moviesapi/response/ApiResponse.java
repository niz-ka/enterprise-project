package com.movies.moviesapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private final String model = "ApiResponse";
    private Integer statusCode;
    private String statusName;
    private String message;
    private List<String> validationMessages;
}
