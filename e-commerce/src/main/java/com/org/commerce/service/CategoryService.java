package com.org.commerce.service;

import com.org.commerce.dto.CategoryResponse;
import com.org.commerce.dto.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    public void createCategory(CreateCategoryRequest request);

    public List<CategoryResponse> getAllCategories();

    public CategoryResponse getCategoryByName(String categoryName);
}
