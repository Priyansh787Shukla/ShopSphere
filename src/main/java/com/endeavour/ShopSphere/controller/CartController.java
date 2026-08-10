package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.AddToCartRequestDTO;
import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.entity.CartItem;
import com.endeavour.ShopSphere.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long cartId, @Valid @RequestBody AddToCartRequestDTO cartItem)
    {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addProductToCart(cartId, cartItem));
    }
}
