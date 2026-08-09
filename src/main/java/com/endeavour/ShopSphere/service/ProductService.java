package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.ProductRequestDTO;
import com.endeavour.ShopSphere.dto.ProductResponseDTO;
import com.endeavour.ShopSphere.entity.Category;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.exception.CategoryNotFoundException;
import com.endeavour.ShopSphere.exception.ProductAlreadyExistsException;
import com.endeavour.ShopSphere.exception.ProductNotFoundException;
import com.endeavour.ShopSphere.repository.CategoryRepository;
import com.endeavour.ShopSphere.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequest)
    {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        if(productRepository.existsByName(productRequest.getName()))
            throw new ProductAlreadyExistsException("Product Already Exists");

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setCategory(category);

        productRepository.save(product);

        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock(),
                product.getDescription(), product.getCategory().getId(), product.getCategory().getName());
    }

    public List<ProductResponseDTO> getAllProducts()
    {
        return productRepository.findAll()
                .stream().map(product -> new ProductResponseDTO(product.getId(), product.getName(),
                        product.getPrice(), product.getStock(), product.getDescription(),
                        product.getCategory().getId(), product.getCategory().getName())).toList();
    }

    public ProductResponseDTO getProductById(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product Does Not Exist"));

        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock(),
                product.getDescription(), product.getCategory().getId(), product.getCategory().getName());
    }
}
