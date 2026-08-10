package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>
{
}
