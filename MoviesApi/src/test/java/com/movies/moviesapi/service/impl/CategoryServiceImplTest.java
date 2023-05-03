package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.model.Category;
import com.movies.moviesapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void Should_ReturnCategoryList_When_Called() {
        List<Category> categories = Arrays.asList(
                new Category("Category1"),
                new Category("Category2")
        );
        Mockito.when(repository.findAll())
                .thenReturn(categories);

        assertEquals(categories, categoryService.getAllCategories());
    }
}