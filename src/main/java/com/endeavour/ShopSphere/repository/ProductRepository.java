package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    public Boolean existsByName(String name);
}
