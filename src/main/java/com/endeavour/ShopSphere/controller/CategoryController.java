package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.entity.Category;
import com.endeavour.ShopSphere.repository.CategoryRepository;
import com.endeavour.ShopSphere.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController
{
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@Valid @RequestBody Category category)
    {
        return categoryService.createCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id)
    {
        return categoryService.getCategoryById(id);
    }
}
