package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
}
