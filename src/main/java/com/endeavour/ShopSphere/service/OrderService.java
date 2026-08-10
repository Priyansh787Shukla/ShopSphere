package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.OrderRequestDTO;
import com.endeavour.ShopSphere.dto.OrderResponseDTO;
import com.endeavour.ShopSphere.entity.*;
import com.endeavour.ShopSphere.exception.CartIsEmptyException;
import com.endeavour.ShopSphere.exception.CartNotFoundException;
import com.endeavour.ShopSphere.exception.OrderNotFoundException;
import com.endeavour.ShopSphere.exception.UserNotFoundException;
import com.endeavour.ShopSphere.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService
{
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;

    public  OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository,
                         CartItemRepository cartItemRepository, CartRepository cartRepository)
    {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request)
    {
        User user = userRepository.findById(request.getUserId()).orElseThrow(()->new UserNotFoundException("User Does Not Exist"));
        Cart cart = cartRepository.findByUserId(request.getUserId()).orElseThrow(()->new CartNotFoundException("Cart Does Not Exist"));
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        if(items.isEmpty())
            throw new CartIsEmptyException("Cart is Empty");
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setUser(user);
        order.setAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);
        Order savedOrder = orderRepository.save(order);
        for(CartItem item : items)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }
        cartItemRepository.deleteAll(items);
        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getUser().getId(), savedOrder.getAmount(),
                savedOrder.getStatus(), savedOrder.getCreatedAt());
    }

    public OrderResponseDTO getOrderById(Long orderId)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found"));
        return new OrderResponseDTO(order.getId(), order.getUser().getId(),
                order.getAmount(), order.getStatus(), order.getCreatedAt());
    }

    public List<OrderResponseDTO> getOrdersByUserId(Long userId)
    {
        userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> new OrderResponseDTO(
                        order.getId(),
                        order.getUser().getId(),
                        order.getAmount(),
                        order.getStatus(),
                        order.getCreatedAt()
                ))
                .toList();
    }

    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order not found"));
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(savedOrder.getId(), savedOrder.getUser().getId(),
                savedOrder.getAmount(), savedOrder.getStatus(), savedOrder.getCreatedAt());
    }
}
