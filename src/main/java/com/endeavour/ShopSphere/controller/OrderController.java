package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.OrderResponseDTO;
import com.endeavour.ShopSphere.entity.OrderStatus;
import com.endeavour.ShopSphere.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService orderService;
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(Authentication authentication, @PathVariable long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(authentication.getName(), id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(authentication.getName()));
    }

    @PutMapping("/{orderId}/status")
    public OrderResponseDTO updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status)
    {
        return orderService.updateOrderStatus(orderId, status);
    }
}
