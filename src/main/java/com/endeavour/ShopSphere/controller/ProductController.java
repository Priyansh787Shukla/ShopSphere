package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.ProductRequestDTO;
import com.endeavour.ShopSphere.dto.ProductResponseDTO;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    private final ProductService productService;
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequest)
    {
        return productService.createProduct(productRequest);
    }
}
