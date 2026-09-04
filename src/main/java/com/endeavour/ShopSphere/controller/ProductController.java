package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.ProductRequestDTO;
import com.endeavour.ShopSphere.dto.ProductResponseDTO;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProducts(name));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@PageableDefault(size = 10, sort = "id") Pageable pageable)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id)
    {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product Deleted Successfully");
    }
}
