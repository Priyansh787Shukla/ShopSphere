package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }
}
