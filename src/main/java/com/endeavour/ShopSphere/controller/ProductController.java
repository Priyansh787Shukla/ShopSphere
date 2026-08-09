package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.ProductRequestDTO;
import com.endeavour.ShopSphere.dto.ProductResponseDTO;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<ProductResponseDTO> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }
}
