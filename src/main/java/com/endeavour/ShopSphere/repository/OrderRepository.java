package com.endeavour.ShopSphere.repository;

import com.endeavour.ShopSphere.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByUserId(long id);
}
