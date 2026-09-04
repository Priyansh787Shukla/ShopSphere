package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.AddToCartRequestDTO;
import com.endeavour.ShopSphere.dto.AddToCartResponseDTO;
import com.endeavour.ShopSphere.dto.CartRequestDTO;
import com.endeavour.ShopSphere.dto.CartResponseDTO;
import com.endeavour.ShopSphere.entity.Cart;
import com.endeavour.ShopSphere.entity.CartItem;
import com.endeavour.ShopSphere.entity.Product;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.exception.*;
import com.endeavour.ShopSphere.repository.CartItemRepository;
import com.endeavour.ShopSphere.repository.CartRepository;
import com.endeavour.ShopSphere.repository.ProductRepository;
import com.endeavour.ShopSphere.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository)
    {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart createCart(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(cartRepository.findByUserId(user.getId()).isPresent())
            throw new CartAlreadyExistsException("User Already has a Cart");

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    public CartItem addProductToCart(Long cartId, AddToCartRequestDTO request, String email)
    {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException("Cart Does Not Exist"));

        if (!cart.getUser().getEmail().equals(email))
            throw new IllegalCartAccessException("You cannot access this cart");

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

    public CartResponseDTO getCartById(Long cartId, String email)
    {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new CartNotFoundException("Cart Does Not Exist"));

        if (!cart.getUser().getEmail().equals(email))
            throw new IllegalCartAccessException("You cannot access this cart");

        List<AddToCartResponseDTO> items = cartItemRepository
                .findByCartId(cartId)
                .stream()
                .map(item -> new AddToCartResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .toList();
        return new CartResponseDTO(cart.getId(), cart.getUser().getId(), items);
    }

    public CartResponseDTO getCartByUserId(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new CartNotFoundException("Cart Does Not Exist"));

        return getCartById(cart.getId(), email);
    }

    public void removeProductFromCart(String email, Long productId)
    {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(()->new CartNotFoundException("Cart Does Not Exist"));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseThrow(()->new CartItemNotFoundException("Product not in the cart"));

        cartItemRepository.delete(cartItem);
    }
}
