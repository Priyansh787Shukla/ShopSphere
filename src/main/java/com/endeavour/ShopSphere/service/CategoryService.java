package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.Category;
import com.endeavour.ShopSphere.exception.CategoryAlreadyExistsException;
import com.endeavour.ShopSphere.exception.CategoryNotFoundException;
import com.endeavour.ShopSphere.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category)
    {
        if(categoryRepository.findByName(category.getName()).isPresent())
            throw new CategoryAlreadyExistsException("Category Already Present");
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id)
    {
        return categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category Not Found"));
    }
}
