package com.movies.moviesapi.service.impl;

import com.movies.moviesapi.entity.Category;
import com.movies.moviesapi.repository.CategoryRepository;
import com.movies.moviesapi.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }
}
