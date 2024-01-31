package com.org.commerce.service.impl;

import com.org.commerce.dto.CategoryResponse;
import com.org.commerce.dto.CreateCategoryRequest;
import com.org.commerce.entity.Category;
import com.org.commerce.repository.CategoryRepository;
import com.org.commerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createCategory(CreateCategoryRequest categoryRequest) {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName(categoryRequest.getName());
        category.setCreatedBy(categoryRequest.getCreatedBy());
        category.setCreatedAt(LocalDateTime.now());
        category.setIsActive(true);

        Category save = categoryRepository.save(category);

        mapToCategoryResponse(save);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(
                category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return mapToCategoryResponse(category);
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
