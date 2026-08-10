package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.AddToCartRequestDTO;
import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.entity.CartItem;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.exception.CartNotFoundException;
import com.endeavour.ShopSphere.exception.ProductNotFoundException;
import com.endeavour.ShopSphere.repository.CartItemRepository;
import com.endeavour.ShopSphere.repository.CartRepository;
import com.endeavour.ShopSphere.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,  CartItemRepository cartItemRepository, ProductRepository productRepository)
    {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart)
    {
        return cartRepository.save(cart);
    }

    public CartItem addProductToCart(Long cartId, AddToCartRequestDTO request)
    {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new CartNotFoundException("Cart Does Not Exist"));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new ProductNotFoundException("Product Does Not Exist"));
        Optional<CartItem> existItem = cartItemRepository.findByCartIdAndProductId(cartId, request.getProductId());
        if(existItem.isPresent())
        {
            CartItem item = existItem.get();
            item.setQuantity(item.getQuantity()+request.getQuantity());
            return cartItemRepository.save(item);
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        return cartItemRepository.save(cartItem);
    }
}
