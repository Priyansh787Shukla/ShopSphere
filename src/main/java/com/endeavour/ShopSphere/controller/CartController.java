package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController
{
    private final CartService cartService;
    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(cart));
    }
}
