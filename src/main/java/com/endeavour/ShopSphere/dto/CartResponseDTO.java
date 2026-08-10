package com.endeavour.ShopSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CartResponseDTO
{
    private Long cartId;
    private Long userId;
    private List<AddToCartResponseDTO> items;
}
