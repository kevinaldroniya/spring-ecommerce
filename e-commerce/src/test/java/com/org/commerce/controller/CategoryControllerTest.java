package com.org.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.commerce.dto.CategoryResponse;
import com.org.commerce.entity.Category;
import com.org.commerce.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();
    }

    @Test
    void testCreateCategorySuccess() throws Exception {
        CategoryResponse category = new CategoryResponse();
        category.setName("unit-test-1");
        category.setCreatedBy("unit-test-1");


        mockMvc.perform(
            post("/api/v1/categories")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(category))
        ).andExpect(
                status().isCreated()
        ).andExpect(
                content().string("OK")
        ).andDo(result -> {
            Category catDB = categoryRepository.findByName("unit-test-1").orElse(null);
            assertNotNull(catDB);

        });
    }

    @Test
    void testGetAllCategories() throws Exception {

        for (int i = 100; i < 105; i++) {
            Category category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setName("unit-test-"+i);
            category.setCreatedBy("unit-test-"+i);
            category.setCreatedAt(LocalDateTime.now());
            category.setIsActive(true);
            categoryRepository.save(category);
        }

        mockMvc.perform(
                get("/api/v1/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andDo(
                result -> {
                   List<CategoryResponse> response = objectMapper.readValue(
                           result.getResponse().getContentAsString(),
                           new TypeReference<List<CategoryResponse>>() {
                           }
                   );
                   assertNotNull(response);
                    List<Category> categories = categoryRepository.findAll();
                    assertEquals(5,categories.size());
                }
        );
    }


    @Test
    void testGetCatByName() throws Exception {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName("unit-test-name");
        category.setCreatedBy("unit-test-name");
        category.setCreatedAt(LocalDateTime.now());
        category.setIsActive(true);
        categoryRepository.save(category);

        mockMvc.perform(
                get("/api/v1/categories/unit-test-name")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                result -> {
                    CategoryResponse response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<CategoryResponse>() {
                            }
                    );
                    assertNotNull(response);
                    Category catDB = categoryRepository.findByName("unit-test-name").orElse(null);
                    assertNotNull(catDB);
                    assertEquals(catDB.getName(),response.getName());
                }
        );
    }

}