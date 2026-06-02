package com.riwi.intro.service;

import com.riwi.intro.exception.ResourceNotFoundException;
import com.riwi.intro.models.Category;
import com.riwi.intro.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAllByOrderByNameAsc();
    }

    public List<Category> findAllById(Collection<Integer> ids) {
        List<Category> categories = new ArrayList<>();
        repository.findAllById(ids).forEach(categories::add);
        return categories;
    }

    public Category findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " was not found"));
    }
}
