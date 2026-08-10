package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{
}
