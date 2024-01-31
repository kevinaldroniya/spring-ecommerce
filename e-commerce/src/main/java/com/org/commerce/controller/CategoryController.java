package com.org.commerce.controller;

import com.org.commerce.dto.CategoryResponse;
import com.org.commerce.dto.CreateCategoryRequest;
import com.org.commerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryRequest request){
        categoryService.createCategory(request);
        return new ResponseEntity<>("OK",HttpStatus.CREATED);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{categoryName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable("categoryName")String categoryName){
        CategoryResponse category = categoryService.getCategoryByName(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
