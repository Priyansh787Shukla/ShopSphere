package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository)
    {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart)
    {
        return cartRepository.save(cart);
    }
}
