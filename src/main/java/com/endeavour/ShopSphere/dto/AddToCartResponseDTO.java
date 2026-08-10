package com.endeavour.ShopSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AddToCartResponseDTO
{
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
}
