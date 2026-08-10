package com.endeavour.ShopSphere.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartRequestDTO
{
    @NotNull
    private Long userId;
}
