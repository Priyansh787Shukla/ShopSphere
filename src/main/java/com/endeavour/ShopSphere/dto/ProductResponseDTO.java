package com.endeavour.ShopSphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductResponseDTO
{
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Long categoryId;
    private String categoryName;
}
