package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    Boolean existsByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
}
