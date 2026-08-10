package com.endeavour.ShopSphere.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder

public class AddToCartRequestDTO
{
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;
}
