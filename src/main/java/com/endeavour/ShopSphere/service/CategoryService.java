package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.Category;
import com.endeavour.ShopSphere.exception.CategoryAlreadyExistsException;
import com.endeavour.ShopSphere.exception.CategoryNotFoundException;
import com.endeavour.ShopSphere.repository.CategoryRepository;
import jakarta.validation.Valid;
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
        if(categoryRepository.existsByName(category.getName()))
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

    public Category updateCategory(Long id, @Valid Category categoryRequest)
    {
        Category category = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        if(!(category.getName().equals(categoryRequest.getName())) && categoryRepository.existsByName(categoryRequest.getName()))
            throw new CategoryAlreadyExistsException("Category with the Same Name Already Exists");

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return categoryRepository.save(category);
    }

    public void  deleteCategoryById(Long id)
    {
        if(!categoryRepository.existsById(id))
            throw new CategoryNotFoundException("Category Not Found");

        categoryRepository.deleteById(id);
    }
}
