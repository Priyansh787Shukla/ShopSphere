package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.AddToCartRequestDTO;
import com.endeavour.ShopSphere.dto.CartRequestDTO;
import com.endeavour.ShopSphere.dto.CartResponseDTO;
import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.entity.CartItem;
import com.endeavour.ShopSphere.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Cart> createCart(Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(authentication.getName()));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long cartId,
                                                     @Valid @RequestBody AddToCartRequestDTO cartItem,
                                                     Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.OK).
                body(cartService.addProductToCart(cartId, cartItem, authentication.getName()));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long cartId, Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartById(cartId, authentication.getName()));
    }

    @GetMapping("/user")
    public ResponseEntity<CartResponseDTO> getCartByUserId(Authentication authentication)
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cartService.getCartByUserId(authentication.getName()));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeProductFromCart(Authentication authentication, @PathVariable Long productId)
    {
        cartService.removeProductFromCart(authentication.getName(), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cart Items Removed");
    }
}
